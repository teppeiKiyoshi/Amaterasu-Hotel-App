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
        throw new Error("Error fetching room types : " , error);
    }

}

// GETS ALL ROOM FROM DB
export async function getAllRooms() {
    try {
        const response = await api.get("/rooms/all-rooms")
        return response.data;
    } catch(error) {
        throw new Error("Error fetching all rooms : ", error);
    }
}