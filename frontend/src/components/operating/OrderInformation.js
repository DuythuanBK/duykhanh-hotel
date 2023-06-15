import { useEffect, useState } from "react"
import backendApi from "../../backendApi";



const RENTING = {
  NONE: 0,
  INFOMATION: 1,
  ORDER: 2
}


function MenuItem({item}) {
  return (
    <tr>
      <td>{item.name}</td>
      <td>{item.price}</td>
      <td>{item.quantity}</td>
      <td>{parseInt(item.price) * parseInt(item.quantity)}</td>
    </tr>
  )
}

export default function OrderInformation({room, handleStatusChanged}) {
  const [items, setItems] = useState([]);

  useEffect(() => {
    backendApi.Order.getAll(room.fullName).then(data => {
      setItems(data.items)
    }).catch(error => {
      
    })
  }, [room.fullName])

  const handleEditingClicked = () => {
    handleStatusChanged(RENTING.ORDER);
  }


  return (
    <>
      <div className="col-12">
        <div className="d-flex align-items-center justify-content-between">
          <p className="lead mb-0">Menu</p>
          <button type="button" className="btn btn-secondary" onClick={handleEditingClicked}>Thay đổi</button>
        </div>
        <hr className='my-2'/>
      </div>
      <table className="table mt-0">
        <thead>
          <tr>
            <th scope="col">Tên</th>
            <th scope="col">Giá</th>
            <th scope="col">Số lượng</th>
            <th scope="col">Thành tiền</th>
          </tr>
        </thead>
        <tbody>
        {
          items.map((item, index) => <MenuItem key={index} item={item}/>)
        }
        </tbody>
      </table>
    </>
  )
}