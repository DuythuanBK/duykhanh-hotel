const RENTING = {
  NONE: 0,
  INFOMATION: 1,
  ORDER: 2
}

const ROOM_STATUS = {
  AVAILABLE: 'AVAILABLE',
  OCCUPIED: 'OCCUPIED',
  CLEANING: 'CLEANING'
}


export default function RoomPreview({ status,
                                      room, 
                                      handleCheckinRoom, 
                                      handleChooseRoomChanged, 
                                      handleStatusChanged,
                                      handleCheckoutRoom,
                                      handleCleanRoomClick }) {
  function getArrivalHours(arrivalTime) {
    if(arrivalTime !== '' && arrivalTime !== null) {
      let startIdx = 11;
      return arrivalTime.slice(startIdx, 16);
    }
    return '--:--';
  }

  return (
    <div className="col col-md-4 mb-4">
      <div className="card">
        <div className={room.status === ROOM_STATUS.AVAILABLE ? 
          "card-header d-flex align-items-center justify-content-between bg-success" : 
          room.status === ROOM_STATUS.OCCUPIED ? 
          "card-header d-flex align-items-center justify-content-between bg-danger" : 
          "card-header d-flex align-items-center justify-content-between bg-warning"}>
          <h1 className="mb-0">{room.name}</h1>
          <p className="mb-0">{room.status === ROOM_STATUS.AVAILABLE ? 'Phòng trống' : 
                                room.status === ROOM_STATUS.OCCUPIED ? 'Đang thuê' : "Đang dọn"}</p>
          <p className="mb-0">{room.usingAirConditioner ? 'Điều hòa' : ''}</p>
        </div>
        <div className="card-body p-2">
          <div className="container-fluid px-0">
            <div className="row">
              <div className="col">
                <p>Giờ vào: </p>
                <p>Tiền phòng: </p>
                <p>Tiền nước: </p>
                <p>Tiền đặt trước: </p>
              </div>
              <div className="col">
                <p>{getArrivalHours(room.arrivalTime)}</p>
                <p>{room.roomFee}</p>
                <p>{room.menuFee}</p>
                <p>{room.deposit}</p>
              </div>
            </div>
            <hr className="my-1"/>
            <div className='row'>
              <div className="d-flex col justify-content-center mx-4">
                {
                  room.status === ROOM_STATUS.AVAILABLE ?
                  <button className="btn btn-primary"
                    onClick={() => {handleCheckinRoom(room)}}>Nhận phòng</button>
                  : room.status === ROOM_STATUS.OCCUPIED ? 
                  <>
                    {/* <button className={status !== RENTING.NONE ? "btn btn-primary me-2 disabled" : "btn btn-primary me-2"}
                      onClick={() => {
                        handleChooseRoomChanged(room);
                        handleStatusChanged(RENTING.INFOMATION)
                      }}>Thông tin
                    </button> */}
                    <button className={status !== RENTING.NONE ? "btn btn-primary disabled" : "btn btn-primary"} onClick={() => {
                      handleChooseRoomChanged(room);
                      handleStatusChanged(RENTING.INFOMATION)
                    }}>Thanh toán</button> 
                  </>
                  : 
                  <button className="btn btn-primary" onClick={() => handleCleanRoomClick(room)}>Dọn phòng</button> 
                }
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}