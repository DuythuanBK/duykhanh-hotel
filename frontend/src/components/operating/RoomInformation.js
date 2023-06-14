import { useState } from "react";

const STATUS = {
  NONE: 0,
  EDIT: 1
}

const RENTING = {
  NONE: 0,
  INFOMATION: 1,
  ORDER: 2
}

const RENTING_TYPE = {
  HOUR: 0,
  OVERNIGHT: 1,
  MONTHLY: 2
}

export default function RoomInformation({room, updateRoomInformation, handleCheckoutRoom, handleStatusChanged}) {
  const [status, setStatus] = useState(STATUS.NONE);
  const [arrivalTime, setArrivalTime] = useState(getArrivalHours(room.arrivalTime));
  const [rentingType, setRentingType] = useState(RENTING_TYPE.HOUR);
  const [roomFee, setRoomFee] = useState(room.roomFee);
  const [menuFee] = useState(room.menuFee);
  const [deposit, setAdvancePayment] = useState(room.deposit);
  const [toatlFee] = useState(room.totalFee);
  const [coldAir, setColdAir] = useState(room.usingAirConditioner);
  

  function getArrivalHours(arrivalTime) {
    if(arrivalTime !== '' && arrivalTime !== null) {
      let startIdx = 11;
      return arrivalTime.slice(startIdx, 16);
    }

    return '';
  }

  function convertTimeToDateTime(time) {
    return room.arrivalTime.slice(0, 11) + time;
  }

  const handleChangeClicked = () => {
    if(status === STATUS.NONE) {
      setStatus(STATUS.EDIT);
    } else {
      const body = {
        usingAirConditioner: coldAir,
        deposit: deposit,
        arrivalTime: convertTimeToDateTime(arrivalTime),
        rentingType: rentingType
      }
      updateRoomInformation(room.fullName, body);
      setStatus(STATUS.NONE);
    }

    
  }

  const handleArrivalTimeChanged = (e) => {
    setArrivalTime(e.target.value);
  }

  const handleRentingTypeChanged = (e) => {
    if(parseInt(e.target.value) === RENTING_TYPE.HOUR) {
      setRentingType(RENTING_TYPE.HOUR);
    } else {
      setRentingType(RENTING_TYPE.OVERNIGHT);
    }
  }

  const handleRoomFeeChanged = (e) => {
    setRoomFee(e.target.value);
  }

  // const handleMenuFeeChanged = (e) => {
  //   setMenuFee(e.target.value);
  // }

  const handleAdvancePayment = (e) => {
    setAdvancePayment(e.target.value);
  }

  // const handleTotalFeeChanged = (e) => {
  //   setTotalFee(e.target.value);
  // }

  const handleColdAirChanged = (e) => {
    setColdAir(e.target.checked);
  }



  return (
    <>
      <div className="d-flex col-12 justify-content-between">
        <h1>{room.name + ' - '+ room.houseType}</h1>
        <div>
          <button className="btn btn-primary my-2 me-2" onClick={() => { handleCheckoutRoom(room); handleStatusChanged(RENTING.NONE) }}>Thanh toán</button>
          <button className="btn btn-primary my-2" onClick={() => handleStatusChanged(RENTING.NONE)}>Xong</button>
        </div>
      </div>
      <div className="col-12 mt-2">
      <div className="d-flex align-items-center justify-content-between">
          <p className="lead mb-0">Thông tin phòng</p>
          <button type="button" className="btn btn-secondary" onClick={handleChangeClicked}>
            {status === STATUS.NONE ?  'Thay đổi' : 'Xong'}
          </button>
        </div>
        <hr />
      </div>
      <div className="col-md-6 mt-0">
        <label htmlFor="arrivalTime" className="d-block form-label">Giờ vào</label>
        { status === STATUS.NONE ?
        <input type="time" className="form-control" id="arrivalTime" value={arrivalTime} disabled/>
        :
        <input type="time" className="form-control" id="arrivalTime" value={arrivalTime}
          onChange={handleArrivalTimeChanged}
        />
        }
      </div>
      <div className="col-md-6 mt-0">
        <label htmlFor="housetype" className="form-label">Kiểu nghỉ</label>
        {status === STATUS.NONE ?
          <select className="form-select" id="housetype" disabled>
              <option value="0">Theo giờ</option>
              <option value="1">Qua đêm</option>
          </select> :
          <select className="form-select" id="housetype" onChange={handleRentingTypeChanged}>
              <option value="0">Theo giờ</option>
              <option value="1">Qua đêm</option>
          </select>
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="roomFee" className="form-label">Tiền Phòng</label>
        { status === STATUS.NONE ?
        <input type="text" className="form-control" id="roomFee" value={roomFee} disabled/>
        :
        <input type="text" className="form-control" id="roomFee" value={roomFee}
          onChange={handleRoomFeeChanged}
        />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="menuFee" className="form-label">Tiền nước</label>
        { status === STATUS.NONE ?
        <input type="text" className="form-control" id="menuFee" value={menuFee} disabled/>
        :
        <input type="text" className="form-control" id="menuFee" value={menuFee} />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="advanceMoney" className="form-label">Tiền đặt trước</label>
        { status === STATUS.NONE ?
        <input type="number" className="form-control" id="advanceMoney" value={deposit} disabled/>
        :
        <input type="number" className="form-control" id="advanceMoney" value={deposit}
          onChange={handleAdvancePayment}
        />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="additionalFee" className="form-label">Phụ phí</label>
        { status === STATUS.NONE ?
        <input type="text" className="form-control" id="additionalFee" disabled/>
        :
        <input type="text" className="form-control" id="additionalFee" />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="totalFee" className="form-label">Tổng tiền</label>
        { status === STATUS.NONE ?
        <input type="text" className="form-control" id="totalFee" value={toatlFee} disabled/>
        :
        <input type="text" className="form-control" id="totalFee" value={toatlFee}/>
        }
      </div>
      <div className="col-md-6">
        <label className="form-label"></label>
        <div className="d-flex align-items-center form-check mt-2">
          { status === STATUS.NONE ?
          <input className="form-check-input" type="checkbox" id="coldAir" checked={coldAir} disabled/>
          :
          <input className="form-check-input" type="checkbox" id="coldAir" checked={coldAir}
            onChange={handleColdAirChanged}
          />
          }
          <label className="form-check-label" htmlFor="coldAir">
            <h4 className='ms-2 mb-0'>ĐIỀU HÒA</h4>
          </label>
        </div>
      </div>
    </>
  )
}