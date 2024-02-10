import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/amaterasu/v1/api",
});

export const getHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

// ADDS A NEW ROOM TO THE DB
export async function addRoom(photo, roomType, roomPrice) {
  const formData = new FormData();
  formData.append("photo", photo);
  formData.append("roomType", roomType);
  formData.append("roomPrice", roomPrice);

  const response = await api.post("/rooms/add", formData);

  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}

// GETS ALL ROOM TYPES FROM DB
export async function getRoomTypes() {
  try {
    const response = await api.get("/rooms/types");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching room types : ", error.message);
  }
}

// GETS ALL ROOM FROM DB
export async function getAllRooms() {
  try {
    const response = await api.get("/rooms/all-rooms");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching all rooms : ", error.message);
  }
}

// DELETE ROOM FROM DB
export async function deleteRoom(roomId) {
  try {
    const response = await api.delete(`/rooms/delete/${roomId}`);
    return response.data;
  } catch (error) {
    throw new Error("Error deleting room : ", error.message);
  }
}

// EDIT AN EXISTING ROOM ON DB
export async function updateRoom(roomId, roomData) {
  const formData = new FormData();
  formData.append("roomType", roomData.roomType);
  formData.append("roomPrice", roomData.roomPrice);
  formData.append("photo", roomData.photo);
  try {
    const response = await api.put(`/rooms/update/${roomId}`, formData);
    return response;
  } catch (error) {
    throw new Error("Error deleting room : ", error.message);
  }
}

// FETCHES A ROOM FROM DB
export async function getRoomById(roomId) {
  try {
    const response = await api.get(`/rooms/room/${roomId}`);
    return response.data;
  } catch (error) {
    throw new Error("There was an error fetching room.", error.message);
  }
}

// BOOKING A ROOM
export async function bookRoom(roomId, booking) {
  try {
    const response = await api.post(`/booking/room/${roomId}/booking`, booking);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data)
      throw new Error(error.response.data);
    throw new Error("There was an error booking the room.", error.message);
  }
}

// GETTING ALL BOOKED ROOMS
export async function getAllBookings() {
  try {
    const response = await api.get("/booking/all-bookings");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching bookings ", error.message);
  }
}

// GETTING A BOOKED ROOM BY CONFIRMATION CODE
export async function getBookingByConfirmationCode(confirmationCode) {
  try {
    const response = await api.get(`/booking/confirmation/${confirmationCode}`);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data)
      throw new Error(error.response.data);
    throw new Error(
      "There was an error finding the booked the room.",
      error.message
    );
  }
}

// CANCELLING AND OR DELETING THE BOOKED ROOM
export async function cancelBooking(bookingId) {
  try {
    const response = await api.delete(`/booking/${bookingId}/delete`);
    return response.data;
  } catch (error) {
    throw new Error(
      "There was an error cancelling your booking ",
      error.message
    );
  }
}

// GET AVAILABLE ROOMS
export async function getAvailableRooms(checkInDate, checkOutDate, roomType) {
  try {
    const response = await api.get(
      `rooms/available-rooms?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`
    );
    return response;
  } catch (error) {
    throw new Error(
      "There was an error retrieving available rooms ",
      error.message
    );
  }
}

// REGISTRATION
export async function registerUser(registration) {
  try {
    const response = await api.post("/auth/register-user", registration);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data)
      throw new Error(error.response.data);
    throw new Error("There was an error registering the user.", error.message);
  }
}

// LOGIN
export async function loginUser(login) {
  const response = await api.post("auth/login", login);
  try {
    if (response.status >= 200 && response.status <= 300) {
      return response.data;
    } else {
      return null;
    }
  } catch (error) {
    console.error(error);
    return null;
  }
}

/*  This is function to get the user profile */
export async function getUserProfile(userId, token) {
  try {
    const response = await api.get(`users/profile/${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    throw new Error(error);
  }
}

/* This isthe function to delete a user */
export async function deleteUser(userId) {
  try {
    const response = await api.delete(`/users/delete/${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    return error.message;
  }
}

/* This is the function to get a single user */
export async function getUser(userId, token) {
  try {
    const response = await api.get(`/users/${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    throw new Error("There was an error retrieving the user :", error);
  }
}

/* This is the function to get user bookings by the user id */
export async function getBookingsByUserId(userId, token) {
  try {
    const response = await api.get(`/bookings/user/${userId}/bookings`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching bookings:", error.message);
    throw new Error("Failed to fetch bookings");
  }
}
