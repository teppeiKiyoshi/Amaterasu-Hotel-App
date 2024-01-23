import { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import { FaEdit, FaEye, FaPlus, FaTrashAlt } from "react-icons/fa";
import { getAllRooms, deleteRoom } from "../utils/ApiFunctions";
import RoomFilter from "../common/RoomFilter";
import RoomPaginator from "../common/RoomPaginator";

const ExistingRoom = () => {
  const [rooms, setRooms] = useState([{ id: "", roomType: "", roomPrice: "" }]);
  const [currentPage, setCurrentPage] = useState(1);
  const [roomsPerPage] = useState(8);
  const [isLoading, setIsLoading] = useState(false);
  const [filteredRooms, setFilteredRooms] = useState([
    { id: "", roomType: "", roomPrice: "" },
  ]);
  const [selectedRoomType, setSelectedRoomType] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  useEffect(() => {
    fetchRooms();
  }, []);

  const fetchRooms = async () => {
    setIsLoading(true);
    try {
      const results = await getAllRooms();
      setRooms(results);
      setIsLoading(false);
    } catch (err) {
      setErrorMsg(err.message);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (selectedRoomType === "") {
      setFilteredRooms(rooms);
    } else {
      const filteredRooms = rooms.filter(
        (room) => room.roomType === selectedRoomType
      );
      setFilteredRooms(filteredRooms);
    }
    setCurrentPage(1);
  }, [rooms, selectedRoomType]);

  const calculateTotalPages = (filteredRooms, roomsPerPage, rooms) => {
    const totalRooms =
      filteredRooms.length > 0 ? filteredRooms.length : rooms.length;
    return Math.ceil(totalRooms / roomsPerPage);
  };

  const indexOfLastRoom = currentPage * roomsPerPage;
  const indexOfFirstRoom = indexOfLastRoom - roomsPerPage;
  const currentRooms = filteredRooms.slice(indexOfFirstRoom, indexOfLastRoom);

  const handlePaginationClick = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleDelete = async (roomId) => {
    try {
      const result = await deleteRoom(roomId);
      if (result === "") {
        setSuccessMsg(`Room number ${roomId} was deleted succesfully.`);
        fetchRooms();
      } else {
        console.error(`Error deleting room : ${result.message}`);
      }
    } catch (error) {
      setErrorMsg(error.message);
    }
    setTimeout(() => {
      setSuccessMsg("");
      setErrorMsg("");
    }, 3000);
  };

  return (
    <>
      <div className="container col-md-8 col-lg-6">
        {successMsg && <p className="alert alert-success mt-5">{successMsg}</p>}

        {errorMsg && <p className="alert alert-danger mt-5">{errorMsg}</p>}
      </div>
      {isLoading ? (
        <p>Loading existing rooms...</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-center mb-3 mt-5">
              <h2>Existing Rooms</h2>
            </div>
            <Row>
              <Col md={6} className="mb-2 md-mb-0">
                <RoomFilter data={rooms} setFilteredData={setFilteredRooms} />
              </Col>

              <Col md={6} className="d-flex justify-content-end">
                <Link to={"/add-room"}>
                  <FaPlus /> Add Room
                </Link>
              </Col>
            </Row>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>Room Type</th>
                  <th>Room Price</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {currentRooms.map((room) => (
                  <tr key={room.id} className="text-center">
                    <td>{room.id}</td>
                    <td>{room.roomType}</td>
                    <td>{room.roomPrice}</td>
                    <td className="gap-2">
                      <Link to={`/edit-room/${room.id}`} className="gap-2">
                        <span className="btn btn-info btn-sm">
                          <FaEye />
                        </span>
                        <span className="btn btn-warning btn-sm ml-5">
                          <FaEdit />
                        </span>
                      </Link>
                      <button
                        className="btn btn-danger btn-sm ml-5"
                        onClick={() => handleDelete(room.id)}
                      >
                        <FaTrashAlt />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            <RoomPaginator
              currentPage={currentPage}
              totalPages={calculateTotalPages(
                filteredRooms,
                roomsPerPage,
                rooms
              )}
              onPageChange={handlePaginationClick}
            />
          </section>
        </>
      )}
    </>
  );
};

export default ExistingRoom;
