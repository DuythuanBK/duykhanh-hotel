import { useState } from "react"
import moment from "moment/moment";
import { Pagination } from "../Pagination";
import backendApi from "../../backendApi"
import { OrderHistory } from "./OrderHistory";
import { CustomerHistory } from "./CustomerHistory"

function HistoryRoom({ room, handleCustomerChoosed, handleOrdersChoosed }) {
  return (
    <tr>
      <td><button className="btn btn-primary" onClick={() => handleCustomerChoosed(room.customer)}>Xem</button></td>
      <td><button className="btn btn-primary" onClick={() => handleOrdersChoosed(room.order)}>Xem</button></td>
      <td><p className="mt-2 mb-0">{moment(room.arrivalTime).format("HH:mm")}</p></td>
      <td><p className="mt-2 mb-0">{moment(room.leaveTime).format("HH:mm")}</p></td>
      <td><p className="mt-2 mb-0">{room.roomFee}</p></td>
      <td><p className="mt-2 mb-0">{room.menuFee}</p></td>
      <td><p className="mt-2 mb-0">{room.totalFee}</p></td>
      <td><h2 className="m-auto">{room.name}</h2></td>
    </tr>
  )
}

export default function HotelHistory() {
  const [rooms, setRooms] = useState([]);
  const [page, setPage] = useState(0);
  const [counts, setCounts] = useState(0);
  const [houseType, setHouseType] = useState('Nhà cũ');
  const [date, setDate] = useState(moment().format('YYYY-MM-DD'));
  const [customer, setCustomer] = useState({});
  const [showCustomerModal, setShowCustomerModal] = useState(false);
  const [showOrderModal, setShowỎderModal] = useState(false);
  const [orders, setOrders] = useState([]);

  const getRoomHistory = (page) => {
    const params = {
      'houseType': houseType,
      'date': date
    }
    backendApi.RoomHistory.get(params, page).then(data => {
      setCounts(data.counts);
      setRooms(data.rooms);

    })
  }

  const handleCustomerModal = (show) => {
    setShowCustomerModal(show);
  }

  const handleOrderModal = (show) => {
    setShowỎderModal(show);
  }

  const handleCustomerChoosed = (customer) => {
    setCustomer(customer);
    handleCustomerModal(true);
  }

  const handleOrdersChoosed = (orders) => {
    setOrders(orders);
    handleOrderModal(true);
  }

  const handleHouseTypeChanged = (e) => {
    setHouseType(e.target.value);
  }

  const handleDateChanged = (e) => {
    setDate(e.target.value);
  }

  const handlePageChanged = (page) => {
    console.log(page);
    setPage(page);
    getRoomHistory(page);
  }

  return (
    <div className="row">
      <div className="d-flex w-50">
        <select className="form-control ms-2 me-3" defaultValue='Nhà cũ' onChange={handleHouseTypeChanged}>
          <option value="Nhà cũ">Nhà cũ</option>
          <option value="Nhà mới">Nhà mới</option>
        </select>
        <input type="date" className="form-control" id="date" value={date} onChange={handleDateChanged}/>
        <button className="btn btn-primary w-25 ms-2" onClick={() => getRoomHistory()}>Tìm kiếm</button>
      </div>

      <div className="container-fluid">
        <table className="table">
          <thead>
            <tr>
              <th scope="col">Thông tin Khách</th>
              <th scope="col">Thông tin đồ uống</th>
              <th scope="col">Giờ vào</th>
              <th scope="col">Giờ ra</th>
              <th scope="col">Tiền phòng</th>
              <th scope="col">Tiền đồ uống</th>
              <th scope="col">Tổng tiền</th>
              <th scope="col">Phòng</th>
            </tr>
          </thead>
          <tbody>
          {
            rooms.map((room, idx) => <HistoryRoom key={idx} room={room} 
                                            handleCustomerChoosed={handleCustomerChoosed} 
                                            handleOrdersChoosed={handleOrdersChoosed}
                                            />)
          }
          </tbody>
        </table>
        <Pagination page={page} totalPage={counts} handlePageChanged={handlePageChanged}/>
      </div>
      {showCustomerModal && (<CustomerHistory customer={customer} handleCustomerModal={handleCustomerModal}/>)}
      {showOrderModal && <OrderHistory orders={orders} handleOrderModal={handleOrderModal}/> }
    </div>
  )
}