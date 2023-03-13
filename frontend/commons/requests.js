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