const API_BASE_URL = "http://localhost:8080";

export const getPlayers = async (page, pageSize) => {
  const response = await fetch(`${API_BASE_URL}/chess_player?page=${page}&size=${pageSize}`);
  return response;
};

export const deletePlayerById = async (id) => {
  const response = await fetch(`${API_BASE_URL}/chess_player/${id}`, {
    method: "DELETE",
  });
  return response;
};

export const getPlayerByID = async (id) => {
  const response = await fetch(`${API_BASE_URL}/chess_player/${id}`);
  return response;
};

export const savePlayer = async (player) => {
  const response = await fetch(`${API_BASE_URL}/chess_player`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(player),
  });
  return response;
};

export const editPlayer = async (player, id) => {
  const response = await fetch(`${API_BASE_URL}/chess_player/${id}`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(player),
  });
  return response;
};