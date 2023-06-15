import React, { useState } from 'react';
import { OrderEditing } from './OrderEditing';
import RoomInformation from './RoomInformation';
import CustomerInformation from './CustomerInformation';
import OrderInformation from './OrderInformation';
import backendApi from '../../backendApi';
import RoomPreview from './RoomPreview';

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

function Information({ room, handleStatusChanged, updateRoomInformation, handleCheckoutRoom }) {
  return (
    <div className="col-4 overflow-auto" style={{ height: "90vh" }}>
      <div className="row g-3">
        <RoomInformation room={room} updateRoomInformation={updateRoomInformation} handleCheckoutRoom={handleCheckoutRoom} handleStatusChanged={handleStatusChanged}/>
        <OrderInformation room={room} handleStatusChanged={handleStatusChanged} />
        <CustomerInformation room={room} />
      </div>
    </div>
  )
}

export default function Renting() {
  const [roomList, setRoomList] = useState([]);
  const [status, setStatus] = useState(RENTING.NONE);
  const [choosedRoom, setChoosedRoom] = useState('');
  const [choosedHouseType, setChoosedHouseType] = useState('Nhà cũ');

  let timerIDs = new Map();

  useState(() => {
      backendApi.Room.getAll().then(data => setRoomList(data.rooms)).catch(error => {
      })
  }, []);

  const handleStatusChanged = (newStatus) => {
    if (status !== newStatus) {
      setStatus(newStatus);
    }
  }

  const handleChooseRoomChanged = (room) => {
    setChoosedRoom(room);
  }

  const handleChooseHouseTypeChanged = (e) => {
    setChoosedHouseType(e.target.value);
  }

  const handleCheckinRoom = (room) => {
    backendApi.Room.checkIn(room.fullName).then(data => {
      const changedRoom = data.room;
      const rooms = roomList.map(r => {
        if(r.fullName === changedRoom.fullName) {
          return changedRoom;
        } else {
          return r;
        }
        
      });
      const id = setInterval(updateFeeOfRoom(room.fullName), 900000);
      timerIDs.set(room.fullName, id);
      setRoomList(rooms);
    }).catch(error => {

    })
  }

  const handleCheckoutRoom = (room) => {
    backendApi.Room.checkout(room.fullName).then(data => {
      const rooms = roomList.map(r => {
        if(r.fullName === data.room.fullName) {
          return data.room;
        } else {
          return r;
        }
      }).catch(error => {
      
      })

      const id = timerIDs.get(room.fullName);
      clearTimeout(id);
      setRoomList(rooms);
    })
  }

  const handleCleanRoomClick = (room) => {
    const updateRoom = {
      status: ROOM_STATUS.AVAILABLE
    };
    backendApi.Room.update(room.fullName, updateRoom).then(data => {
      const room = data.room;
      const newRooms = roomList.map(r => {
        if(r.fullName === room.fullName) {
          return room;
        }
        return r;
      }).catch(error => {
      
      });
      setRoomList(newRooms);
    })
  }

  const getRoomInformation = async (fullName) => {
    try {
      const data = await backendApi.Room.getRoom(fullName);
      return data.room;
    } catch(error) {

    }
  }

  const updateFeeOfRoom = async (fullName) => {
    const room = await getRoomInformation(fullName);
    const newRooms = roomList.map(r => {
      if(r.fullName === room.fullName) {
        return room;
      }
      return r;
    });
    setRoomList(newRooms);
  }

  const updateRoomInformation = (fullName, body) => {
    backendApi.Room.update(fullName, body).then(data => {
      const room = data.room;
      const newRooms = roomList.map(r => {
        if(r.fullName === room.fullName) {
          return room;
        }
        return r;
      }).catch(error => {

      });
      setRoomList(newRooms);
    })
  }

  return (
    <div className="row">
      <div className="container-fluid">
        <div className="row">
          <div className="col-8">
          <select className="ms-4 mb-2 fs-4" style={{width: "150px", height: "40px"}} defaultValue='Nhà cũ' onChange={handleChooseHouseTypeChanged}>
            <option value="Nhà cũ">Nhà cũ</option>
            <option value="Nhà mới">Nhà mới</option>
          </select>
          <div className="container-fluid">
            <div className="row">
            {
              roomList.filter(it => it.houseType === choosedHouseType).map((room, index) => <RoomPreview key={index} status={status} room={room}
                handleCheckinRoom={handleCheckinRoom}
                handleChooseRoomChanged={handleChooseRoomChanged}
                handleStatusChanged={handleStatusChanged}
                handleCheckoutRoom={handleCheckoutRoom}
                handleCleanRoomClick={handleCleanRoomClick}
              />)
            }
            </div>
          </div>
          </div>
          {
            status === RENTING.INFOMATION ?
              <Information room={choosedRoom} handleStatusChanged={handleStatusChanged} updateRoomInformation={updateRoomInformation}
                handleCheckoutRoom={handleCheckoutRoom}
              /> :
              status === RENTING.ORDER ?
                <OrderEditing room={choosedRoom} handleStatusChanged={handleStatusChanged}
                  updateFeeOfRoom={updateFeeOfRoom}
                /> :
                <></>
          }
        </div>
      </div>
    </div>
  )
}