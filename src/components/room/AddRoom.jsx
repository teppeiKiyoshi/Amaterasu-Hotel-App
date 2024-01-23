import { useState } from "react";
// import { Link } from 'react-router-dom'
import { addRoom } from "../utils/ApiFunctions";
import RoomTypeSelector from "../common/RoomTypeSelector";

const AddRoom = () => {
  const [newRoom, setNewRoom] = useState({
    photo: null,
    roomType: "",
    roomPrice: "",
  });

  const [imagePreview, setImagePreview] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleRoomInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    if (name === "roomPrice") {
      if (!isNaN(value)) {
        value = parseInt(value);
      } else {
        value = "";
      }
    }
    setNewRoom({ ...newRoom, [name]: value });
  };

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setNewRoom({ ...newRoom, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const success = await addRoom(
        newRoom.photo,
        newRoom.roomType,
        newRoom.roomPrice
      );
      console.log("laman ni success : " ,newRoom.photo,
      newRoom.roomType,
      newRoom.roomPrice);
      if (success != undefined) {
        console.log("success on adding");
        setSuccessMsg("A new room was succesfully added to the database.");
        setNewRoom({ photo: null, roomType: "", roomPrice: "" });
        setImagePreview("");
        setErrorMsg("");
      } else {
        setErrorMsg("There was an error adding room to the database.");
        console.log(errorMsg);
      }
    } catch (error) {
      setErrorMsg(error.message);
      console.log("error on submit : ", error.message);
    }
  };

  return (
    <>
			<section className="container mt-5 mb-5">
				<div className="row justify-content-center">
					<div className="col-md-8 col-lg-6">
						<h2 className="mt-5 mb-2">Add a New Room</h2>
						{successMsg && (
							<div className="alert alert-success fade show"> {successMsg}</div>
						)}

						{errorMsg && <div className="alert alert-danger fade show"> {errorMsg}</div>}

						<form onSubmit={handleSubmit}>
							<div className="mb-3">
								<label htmlFor="roomType" className="form-label">
									Room Type
								</label>
								<div>
									<RoomTypeSelector
										handleRoomInputChange={handleRoomInputChange}
										newRoom={newRoom}
									/>
								</div>
							</div>
							<div className="mb-3">
								<label htmlFor="roomPrice" className="form-label">
									Room Price
								</label>
								<input
									required
									type="number"
									className="form-control"
									id="roomPrice"
									name="roomPrice"
									value={newRoom.roomPrice}
									onChange={handleRoomInputChange}
								/>
							</div>

							<div className="mb-3">
								<label htmlFor="photo" className="form-label">
									Room Photo
								</label>
								<input
									required
									name="photo"
									id="photo"
									type="file"
									className="form-control"
									onChange={handleImageChange}
								/>
								{imagePreview && (
									<img
										src={imagePreview}
										alt="Preview  room photo"
										style={{ maxWidth: "400px", maxHeight: "400px" }}
										className="mb-3"></img>
								)}
							</div>
							<div className="d-grid gap-2 d-md-flex mt-2">
								{/* <Link to={"/existing-rooms"} className="btn btn-outline-info">
									Existing rooms
								</Link> */}
								<button type="submit" className="btn btn-outline-primary ml-5">
									Save Room
								</button>
							</div>
						</form>
					</div>
				</div>
			</section>
		</>
  );
};


export default AddRoom;
