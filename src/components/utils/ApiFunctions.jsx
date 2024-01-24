import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8080/amaterasu/v1/api"
})

// ADDS A NEW ROOM TO THE DB
export async function addRoom(photo, roomType, roomPrice) {
    const formData = new FormData();
    formData.append("photo", photo);
    formData.append("roomType", roomType);
    formData.append("roomPrice", roomPrice);

    const response = await api.post("/rooms/add" ,formData);

    if(response.status === 201){
        return true;
    } else {
        return false;
    }
}

// GETS ALL ROOM TYPES FROM DB
export async function getRoomTypes(){
    try{
        const response = await api.get("/rooms/types");
        return response.data;
    } catch(error) {
        throw new Error("Error fetching room types : " , error.message);
    }

}

// GETS ALL ROOM FROM DB
export async function getAllRooms() {
    try {
        const response = await api.get("/rooms/all-rooms")
        return response.data;
    } catch(error) {
        throw new Error("Error fetching all rooms : ", error.message);
    }
}

// DELETE ROOM FROM DB
export async function deleteRoom(roomId) {
    try {
        const response = await api.delete(`/rooms/delete/${roomId}`)
        return response.data;
    } catch(error) {
        throw new Error("Error deleting room : ", error.message);
    }
}

// EDIT AN EXISTING ROOM ON DB
export async function updateRoom(roomId, roomData) {
    const formData = new FormData();
    formData.append("roomType", roomData.roomType)
    formData.append("roomPrice", roomData.roomPrice)
    formData.append("photo", roomData.photo)
    try {
        const response = await api.put(`/rooms/update/${roomId}`, formData)
        return response;
    } catch(error) {
        throw new Error("Error deleting room : ", error.message);
    }
}

// FETCHES A ROOM FROM DB
export async function getRoomById(roomId){
    try {
        const response = await api.get(`/rooms/room/${roomId}`)
        return response.data;
    }catch(error) {
        throw new Error("There was an error fetching room." , error.message)
    }
}