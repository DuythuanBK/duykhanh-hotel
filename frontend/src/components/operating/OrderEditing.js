import { useEffect, useState } from "react";
import backendApi from "../../backendApi";

const RENTING = {
  NONE: 0,
  INFOMATION: 1,
  ORDER: 2
}

function HotelItem({ item, handleAddItem }) {
  return (
    <tr>
      <td>{item.name}</td>
      <td>{item.price}</td>
      <td>
        <button type="btn" className="btn btn-primary"
          onClick={(e) => { handleAddItem(item) }}>Thêm</button>
      </td>
    </tr>
  )
}

function HotelMenu({ items, handleAddItem }) {

  const _handleAddClick = (item) => {
    let order = {
      name: item.name,
      price: item.price
    };
    handleAddItem(order);
  }

  return (
    <>
      <div className="col-12">
        <div className="d-flex align-items-center justify-content-between">
          <p className="lead mb-0">Danh sách đồ uống</p>
        </div>
        <hr className='my-2' />
      </div>
      <table className="table mt-0">
        <thead>
          <tr>
            <th scope="col">Tên</th>
            <th scope="col">Giá</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {
            items.map((item, index) => <HotelItem key={index} item={item} handleAddItem={_handleAddClick} />)
          }
        </tbody>
      </table>
    </>
  )
}

function OrderItem({ item, handleQuantityChanged, handleDeleteItem }) {
  const [quantity, setQuantity] = useState(item.quantity);

  const _handleQuantityChanged = (e) => {
    if (e.target.value !== "") {
      setQuantity(e.target.value);
      let newItem = { ...item };
      newItem.quantity = quantity;
      handleQuantityChanged(newItem);
    }
  }

  return (
    <tr>
      <td>{item.name}</td>
      <td>{item.price}</td>
      <td><input type="number" id="tentacles" name="tentacles"
        min="1" value={quantity} onChange={_handleQuantityChanged} /></td>
      <td>{parseInt(item.price) * parseInt(quantity)}</td>
      <td>
        <button className="btn btn-danger" onClick={() => handleDeleteItem(item)}>Xóa</button>
      </td>
    </tr>
  )
}

function Orders({ items, handleQuantityChanged, handleDeleteItem }) {
  return (
    <>
      <div className="col-12">
        <div className="d-flex align-items-center justify-content-between">
          <p className="lead mb-0">Đồ uống đã chọn:</p>
        </div>
        <hr className='my-2' />
      </div>
      <table className="table mt-0">
        <thead>
          <tr>
            <th scope="col">Tên</th>
            <th scope="col">Giá</th>
            <th scope="col">Số lượng</th>
            <th scope="col">Thành tiền</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {
            items.map((item, index) => <OrderItem key={index} item={item} handleQuantityChanged={handleQuantityChanged} handleDeleteItem={handleDeleteItem} />)
          }
        </tbody>
      </table>
    </>
  )
}

export function OrderEditing({ room, handleStatusChanged, updateFeeOfRoom }) {
  const [orders, setOrders] = useState([]);
  const [menu, setMenu] = useState([]);

  useEffect(() => {
    backendApi.Order.getAll(room.fullName).then(data => {
      setOrders(data.items)
    }).catch(error => {

    });

    backendApi.Item.getAll().then(data => {
      setMenu(data.items)
    }).catch(error => {
      
    })
  }, [room.fullName])

  const updateOrderToDB = async () => {
    for(const item of orders) {
      if(item.hasOwnProperty('id')) {
        await backendApi.Order.update(room.fullName, item);
      } else {
        await backendApi.Order.post(room.fullName, item);
      }
    }
    console.log("finish update to db");
  }

  const handleFinshClick = async () => {
    // update database
    await updateOrderToDB();
    await updateFeeOfRoom(room.fullName);
    handleStatusChanged(RENTING.NONE)
  }

  const handleDeleteItem = (item) => {
    backendApi.Order.delete(room.fullName, item.id);
    let newList = orders.filter(it => it.id !== item.id);
    setOrders(newList);
  }

  const handleQuantityChanged = (item) => {
    let newList = orders.map((it) => {
      if (it.id === item.id) {
        it.quantity = item.quantity
      }
      return it;
    });
    setOrders(newList)
  }

  const handleAddItem = (item) => {
    let list = orders.filter(it => it.name === item.name);
    if (list.length === 0) {
      let newItem = { ...item };
      newItem.quantity = 1;
      setOrders([...orders, newItem]);
    }
  }

  return (
    <div className="col-4 overflow-auto" style={{ height: "90vh" }}>
      <div className="row g-3">
        <div className="d-flex justify-content-end">
          <button className="btn btn-primary" onClick={handleFinshClick}>Xong</button>
        </div>
        <Orders items={orders} handleQuantityChanged={handleQuantityChanged} handleDeleteItem={handleDeleteItem} />
        <HotelMenu items={menu} handleAddItem={handleAddItem} />
      </div>
    </div>
  )
}