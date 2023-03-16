import { deletePlayerById, getPlayers } from "./../../commons/requests.js";

const tableBody = document.querySelector(".tbody");
const pagination = document.querySelector(".pagination");
const addBtn = document.querySelector("#addBtn");
const editBtn = document.querySelector("#editBtn");
const deleteBtn = document.querySelector("#deleteBtn");
const pageSizeSelect = document.querySelector("#pageSizeSelect");
const table = document.querySelector('table');

const urlParams = new URLSearchParams(window.location.search);
const providedPage = urlParams.get('page');
const providedSize = urlParams.get('size');

let pageSize = 3;
let currentPage = 0;
let totalPages = 0;
let selectedPlayerId = null;
let lastPageLeftItemsToAdd;
let totalItems;

if (providedPage !== null && providedSize !== null) {
    currentPage = parseInt(providedPage);
    pageSize = parseInt(providedSize);
}

pageSizeSelect.value = pageSize;

init();

function init() {
    loadPlayers(currentPage);
    pageSizeSelect.addEventListener("change", onPageSizeChange);
    addBtn.addEventListener("click", onAddButtonClick);
    editBtn.addEventListener("click", onEditButtonClick);
    deleteBtn.addEventListener("click", onDeleteButtonClick);
    document.addEventListener('click', onClickOutsideTable);
}

async function loadPlayers(page) {
    try {
        const response = await getPlayers(page, pageSize);
        const players = await response.json();
        totalItems = parseInt(response.headers.get("X-Total-Count"));
        totalPages = Math.ceil(totalItems / pageSize);
        lastPageLeftItemsToAdd = totalItems % pageSize; // Get items in last page (for adding user logic)
        // Update the player table with the data
        tableBody.innerHTML = "";
        players.forEach(player => {
            const row = createTableRow(player);
            tableBody.appendChild(row);
        });
        // Update the pagination links
        pagination.innerHTML = "";
        const pageRange = calculatePageRange(currentPage, totalPages);
        if (pageRange[0] > 0) {
            const prevLink = createPaginationLink(currentPage - pageRange.length, "Prev");
            pagination.appendChild(prevLink);
        }
        for (let i = pageRange[0]; i <= pageRange[pageRange.length - 1]; i++) {
            const link = createPaginationLink(i);
            pagination.appendChild(link);
        }
        if (pageRange[pageRange.length - 1] < totalPages - 1) {
            const nextLink = createPaginationLink(currentPage + pageRange.length, "Next");
            pagination.appendChild(nextLink);
        }
        // Update the delete / edit button state
        updateDeleteEditButtonsState();
    } catch (error) {
        console.error(error);
    }
}

function calculatePageRange(currentPage, totalPages) {
    const pageRange = [];
    const maxPages = 5;
    const startPage = Math.floor(currentPage / maxPages) * maxPages;
    const lastPage = Math.min(startPage + maxPages - 1, totalPages - 1);
    for (let i = startPage; i <= lastPage; i++) {
        pageRange.push(i);
    }
    return pageRange;
}

function createPaginationLink(pageIndex, text) {
    const link = document.createElement("a");
    link.href = "#";
    link.textContent = text || pageIndex + 1;
    link.addEventListener("click", (event) => {
        event.preventDefault();
        currentPage =
            text === "Prev"
                ? calculatePageRange(currentPage, totalPages)[0] - 1
                : text === "Next"
                    ? calculatePageRange(currentPage, totalPages)[4] + 1
                    : pageIndex;
        loadPlayers(currentPage);
    });
    link.classList.toggle("active", pageIndex === currentPage || text === "Prev" || text === "Next");
    link.classList.toggle("nav-button", text === "Prev" || text === "Next");
    return link;
}


function createTableRow(player) {
    const row = document.createElement("tr");
    row.innerHTML = `
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

function onPageSizeChange(event) {
    pageSize = parseInt(event.target.value);
    currentPage = 0;
    selectedPlayerId = null;
    loadPlayers(currentPage);
}

async function onDeleteButtonClick() {
    try {
        const id = selectedPlayerId;
        const response = await deletePlayerById(selectedPlayerId);
        if (response.ok) {
            alert(`Player with id: ${id} deleted successfully!`);
            // switch to lower page if was deleted last element in current page
            if (currentPage == totalPages) {
                currentPage = totalPages - 1;
            }
            loadPlayers(currentPage);
        } else {
            console.error(response.status);
        }
    } catch (error) {
        console.error(error);
    }
}

function onAddButtonClick() {
    const queryParams = new URLSearchParams({
        totalPages: totalPages,
        fromPageSize: pageSize,
        lastPageLeftItemsToAdd: lastPageLeftItemsToAdd
    });
    window.location.replace(`../chess-user-form/chess-user-form.html?${queryParams}`);
}


function onEditButtonClick() {
    const queryParams = new URLSearchParams({
        id: selectedPlayerId,
        fromPage: currentPage,
        fromPageSize: pageSize,
    });
    window.location.replace(`../chess-user-form/chess-user-form.html?${queryParams}`);
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

