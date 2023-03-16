import { editPlayer, getPlayerByID, savePlayer } from "../../commons/requests.js";

const form = document.getElementById("playerForm").querySelector("form");

const queryParams = new URLSearchParams(window.location.search);
const playerId = queryParams.get('id');
const fromPage = queryParams.get('fromPage');
const fromPageSize = queryParams.get('fromPageSize');
const totalPages = queryParams.get('totalPages');
const lastPageLeftItemsToAdd = queryParams.get('lastPageLeftItemsToAdd');
const titleAdd = document.getElementById("addPlayerTitle");
const titleEdit = document.getElementById("editPlayerTitle");
const returnUrl = `./../chess-users-list/chess-users-list.html?page=${fromPage}&size=${fromPageSize}`;

titleEdit.toggleAttribute("hidden", playerId === null);
titleAdd.toggleAttribute("hidden", playerId !== null);

let oldPlayerData;
let callback;

if (playerId !== null) {
    const response = await getPlayerByID(playerId);
    oldPlayerData = await response.json();
    form.playerName.value = oldPlayerData.name;
    form.playerSurname.value = oldPlayerData.surname;
    form.playerEmail.value = oldPlayerData.email;
    form.playerPersonCode.value = oldPlayerData.personCode;
    form.startPlayChessFromDate.value = oldPlayerData.startPlayChessFromDate;
    callback = async (player, playerId) => {
        return await editPlayer(player, playerId);
    }
} else {
    callback = async (player) => {
        return await savePlayer(player);
    }
}

const lastDay = new Date();
lastDay.setDate(lastDay.getDate());

const input = document.getElementById("startPlayChessFromDate");
input.max = lastDay.toISOString().split("T")[0];


const handleFormSubmit = async (callback) => {
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        validatePersonCode();

        if (!form.checkValidity()) {
            e.stopPropagation();
            form.classList.add('was-validated');
            return;
        }

        const player = setPlayer();
        if (!player) {
            const message = `No fields modified for player id: ${playerId}! Returning to main list.`;
            alert(message);
            window.location.replace(returnUrl);
            return;
        }

        try {
            const response = await callback(player, playerId);
            const page = playerId ? fromPage : totalPages - (lastPageLeftItemsToAdd == 0 ? 0 : 1);
            const message = playerId
                ? `Player with id: ${playerId} modified successfully!`
                : `New player added successfully!`;
            alert(message);
            const url = `./../chess-users-list/chess-users-list.html?page=${page}&size=${fromPageSize}`;
            window.location.replace(url);
        } catch (error) {
            console.error(error);
        }
    });
};




const handleCancelButton = () => {
    document.getElementById("cancelButton").addEventListener("click", () => {
        window.location.replace(returnUrl);
    });
};

function setPlayer() {
    if (playerId) {
        const player = {
            name:
                oldPlayerData.name !== form.playerName.value
                    ? form.playerName.value
                    : undefined,
            surname:
                oldPlayerData.surname !== form.playerSurname.value
                    ? form.playerSurname.value
                    : undefined,
            email:
                oldPlayerData.email !== form.playerEmail.value
                    ? form.playerEmail.value
                    : undefined,
            personCode:
                oldPlayerData.personCode !== form.playerPersonCode.value
                    ? form.playerPersonCode.value
                    : undefined,
            startPlayChessFromDate:
                oldPlayerData.startPlayChessFromDate !== form.startPlayChessFromDate.value
                    ? form.startPlayChessFromDate.value
                    : undefined,
        };
        const allFieldsUndefined = Object.values(player).every(value => value === undefined);
        if (allFieldsUndefined) {
            return null;
        } else {
            return player;
        }
    } else {
        const player = {
            name: form.playerName.value,
            surname: form.playerSurname.value,
            email: form.playerEmail.value,
            personCode: form.playerPersonCode.value,
            startPlayChessFromDate: form.startPlayChessFromDate.value,
        };
        return player;
    }
}

function isValidPersonCode(personCode) {
    // Check if the person code is a string of 11 characters
    if (typeof personCode !== "string" || personCode.length !== 11) {
        return false;
    }
    // allow unmodified value
    if (personCode === "***********") {
        return true;
    }
    // Check the first character (sex)
    const sex = parseInt(personCode.charAt(0));
    if (isNaN(sex) || (sex < 3 || sex > 6)) {
        return false;
    }
    // Check the second and third characters (year)
    const year = parseInt(personCode.substr(1, 2));
    const currentYear = new Date().getFullYear() % 100;
    if (isNaN(year) || (sex < 5 && (year < 0 || year > 99)) || (sex >= 5 && (year < 0 || year > currentYear))) {
        return false;
    }
    // Check the fourth and fifth characters (month)
    const month = parseInt(personCode.substr(3, 2));
    const currentMonth = new Date().getMonth() + 1;
    if (isNaN(month) || month < 1 || (year === currentYear && month > currentMonth) || month > 12) {
        return false;
    }
    // Check the sixth and seventh characters (day)
    const day = parseInt(personCode.substr(5, 2));
    const currentDay = new Date().getDate();
    const isLeapYear = ((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0);
    const maxDaysInMonth = new Date(year, month, 0).getDate();
    if (isNaN(day) || day < 1 || (year === currentYear && month === currentMonth && day > currentDay) || day > maxDaysInMonth) {
        return false;
    }
    // Check the remaining four characters (fourDigits)
    const fourDigits = personCode.substr(7, 4);
    if (!/^\d{4}$/.test(fourDigits)) {
        return false;
    }
    // If all checks passed, the person code is valid
    return true;
}

function validatePersonCode() {
    const personCodeElement = document.getElementById("playerPersonCode");

    function validateInput() {
        const personCode = personCodeElement.value;
        const isValid = isValidPersonCode(personCode);
        personCodeElement.setCustomValidity(isValid ? "" : "Invalid input");
    }

    personCodeElement.addEventListener("input", validateInput);
    validateInput();
}

(async () => {
    handleCancelButton();
    await handleFormSubmit(callback);
})();