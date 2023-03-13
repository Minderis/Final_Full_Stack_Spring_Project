import { deletePlayerById, getPlayers } from "./../../commons/requests.js";

const tableBody = document.querySelector(".tbody");
const pagination = document.querySelector(".pagination");
const addBtn = document.querySelector("#addBtn");
const editBtn = document.querySelector("#editBtn");
const deleteBtn = document.querySelector("#deleteBtn");
const pageSizeSelect = document.querySelector("#pageSizeSelect");
const table = document.querySelector('table');

let pageSize = 3;
pageSizeSelect.value = '3';
let currentPage = 0;
let totalPages = 0;
let selectedPlayerId = null;

init();

function init() {
    loadPlayers(currentPage);
    pageSizeSelect.addEventListener("change", onPageSizeChange);
    deleteBtn.addEventListener("click", onDeleteButtonClick);
    document.addEventListener('click', onClickOutsideTable);
}

async function loadPlayers(page) {
    try {
        const response = await getPlayers(page, pageSize);
        const players = await response.json();

        // Update the player table with the data
        tableBody.innerHTML = "";
        players.forEach(player => {
            const row = createTableRow(player);
            tableBody.appendChild(row);
        });

        // Get the total number of players from the API and calculate the total number of pages
        const totalCount = parseInt(response.headers.get("X-Total-Count"));
        totalPages = Math.ceil(totalCount / pageSize);

        // Update the pagination links
        pagination.innerHTML = "";
        for (let i = 0; i < totalPages; i++) {
            const link = createPaginationLink(i);
            pagination.appendChild(link);
        }

        // Update the delete / edit button state
        updateDeleteEditButtonsState();
    } catch (error) {
        console.error(error);
    }
}

function createTableRow(player) {
    const row = document.createElement("tr");
    row.innerHTML = `
    <td>${player.id}</td>
    <td>${player.name}</td>
    <td>${player.surname}</td>
    <td>${player.email}</td>
    <td>${player.sex}</td>
    <td>${player.age}</td>
    <td>${player.duration}</td>
  `;
    row.addEventListener("click", () => {
        // Set the selected player ID when a row is clicked
        selectedPlayerId = player.id;
        const elements = document.querySelectorAll(".selected");
        elements.forEach(element => {
            element.classList.remove("selected");
        });
        updateDeleteEditButtonsState();
        row.classList.add("selected");
    });
    return row;
}

function createPaginationLink(pageIndex) {
    const link = document.createElement("a");
    link.href = "#";
    link.textContent = pageIndex + 1;
    link.addEventListener("click", event => {
        event.preventDefault();
        currentPage = pageIndex;
        loadPlayers(currentPage);
    });
    if (pageIndex === currentPage) {
        link.classList.add("active");
    }
    return link;
}

function onPageSizeChange(event) {
    pageSize = parseInt(event.target.value);
    currentPage = 0;
    loadPlayers(currentPage);
}

async function onDeleteButtonClick() {
    try {
        const response = await deletePlayerById(selectedPlayerId);
        if (response.ok) {
            loadPlayers(currentPage);
        } else {
            console.error(response.status);
        }
    } catch (error) {
        console.error(error);
    }
}

function updateDeleteEditButtonsState() {
    deleteBtn.disabled = editBtn.disabled = !selectedPlayerId;
}

function onClickOutsideTable(event) {
    // check if the target element is a child of the table
    if (!table.contains(event.target)) {
        // the click was outside of the table
        const elements = document.querySelectorAll(".selected");
        elements.forEach(element => {
            element.classList.remove("selected");
        });
        selectedPlayerId = null;
        updateDeleteEditButtonsState();
    }
}
