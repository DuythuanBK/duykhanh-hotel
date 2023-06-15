import { useEffect, useState } from "react"
import backendApi from "../../backendApi";

const EDITING_STATUS = {
  NONE: 0,
  ADD: 1,
  EDIT: 2
}

function MainDisplay({ items, updateChanging, handleEditClick, handleDeleteClick }) {
  function _handleAddClick() {
    updateChanging(EDITING_STATUS.ADD);
  }

  function _handleEditClick(item) {
    updateChanging(EDITING_STATUS.EDIT);
    handleEditClick(item);
  }

  return (
    <div className="col-8">
      <button type="button" className="btn btn-primary" onClick={_handleAddClick}>Thêm</button>
      <div className="overflow-scroll" style={{ height: '90vh' }}>
        <table className="table">
          <thead>
            <tr>
              <th scope="col">Thứ tự</th>
              <th scope="col">Tên</th>
              <th scope="col">Giờ đầu</th>
              <th scope="col">Giờ tiếp theo</th>
              <th scope="col">Qua đêm</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {items.map((item, index) => <Item key={index} index={index + 1} item={item} handleEditClick={_handleEditClick} handleDeleteClick={handleDeleteClick} />)}
          </tbody>
        </table>
      </div>
    </div>
  )
}

function Editing({ status, item, updateChanging, handleFinishEdit }) {
  const [name, setName] = useState('');
  const [firstHour, setFirstHour] = useState('');
  const [nextHour, setNextHour] = useState('');
  const [overnigt, setOvernigt] = useState('');

  useEffect(() => {
    if (status === EDITING_STATUS.EDIT) {
      setName(item.name);
      setFirstHour(item.firstHour);
      setNextHour(item.nextHour);
      setOvernigt(item.overnight);
    }
  }, [item.firstHour, item.name, item.nextHour, item.overnight, status])

  function handleEditClick() {
    let newItem = item;
    newItem.name = name;
    newItem.firstHour = firstHour;
    newItem.nextHour = nextHour;
    newItem.overnight = overnigt;

    handleFinishEdit(newItem);
    updateChanging(EDITING_STATUS.NONE);
  }

  function handleCancelClick() {
    updateChanging(EDITING_STATUS.NONE);
  }

  const handleNameChange = (event) => {
    setName(event.target.value);
  }

  const handleFirstHourChange = (event) => {
    setFirstHour(event.target.value);
  }

  const handleNextHourChange = (event) => {
    setNextHour(event.target.value);
  }

  const handleOvernigthChange = (event) => {
    setOvernigt(event.target.value);
  }

  return (
    <div className="col-4">
      <form className="row g-3">
        <div className="col-md-6">
          <label htmlFor="name" className="form-label">Tên</label>
          <input type="" className="form-control" id="name" value={name} onChange={handleNameChange} />
        </div>
        <div className="col-md-6">
          <label htmlFor="firstHour" className="form-label">Giờ đầu</label>
          <input type="" className="form-control" id="firstHour" value={firstHour} onChange={handleFirstHourChange} />
        </div>
        <div className="col-md-6">
          <label htmlFor="nextHour" className="form-label">Giờ tiếp theo</label>
          <input type="" className="form-control" id="nextHour" value={nextHour} onChange={handleNextHourChange} />
        </div>
        <div className="col-md-6">
          <label htmlFor="overnight" className="form-label">Qua đêm</label>
          <input type="" className="form-control" id="overnight" value={overnigt} onChange={handleOvernigthChange} />
        </div>
        <div className="col-md-12 d-flex justify-content-center">
          <button type="button" className="btn btn-primary me-1 w-25" onClick={handleEditClick}>{status === EDITING_STATUS.ADD ? 'Thêm' : 'Sửa'}</button>
          <button type="button" className="btn btn-primary ms-1 w-25" onClick={handleCancelClick}>Hủy</button>
        </div>
      </form>
    </div>
  )
}

function Item({ index, item, handleEditClick, handleDeleteClick }) {

  function _handleOnClick(item) {
    handleEditClick(item);
  }

  return (
    <tr>
      <th scope="row">{index}</th>
      <td>{item.name}</td>
      <td>{item.firstHour}</td>
      <td>{item.nextHour}</td>
      <td>{item.overnight}</td>
      <td>
        <button type="button" className="btn btn-primary" onClick={() => _handleOnClick(item)}>Sửa</button>
        <button type="button" className="btn btn-danger" onClick={() => handleDeleteClick(item)}>Xóa</button>
      </td>
    </tr>
  )
}



export default function RoomPrice() {
  const [editingStatus, setEditingStatus] = useState(EDITING_STATUS.NONE);
  const [items, updateItems] = useState([]);
  const [editingItem, setEditingItem] = useState({ name: '' })

  useEffect(() => {
    backendApi.RoomPrice.getAll().then(data => updateItems(data.roomPrices)).catch(error => {});
  }, [])

  function updateChanging(status) {
    if (status === EDITING_STATUS.NONE) {
      setEditingItem({ name: '' })
    }
    setEditingStatus(status);
  }

  function handleEditClick(item) {
    setEditingItem(item)
  }
  const handleDeleteClick = (item) => {
    backendApi.RoomPrice.delete(item.id);
    let newItems = items.filter(it => it.id !== item.id);
    updateItems(newItems);
  }

  function handleUpdateItemList(item) {
    backendApi.RoomPrice.update(item);
    let newItems = items.map(it => {
      if (item.id === it.id) {
        it.name = item.name;
      }
      return it;
    })

    updateItems(newItems);
  }

  function handleAddItem(item) {
    backendApi.RoomPrice.post(item).then(data => {
      console.log(data);
      updateItems([...items, data.roomPrice]);
    });
  }

  function handleFinishEdit(item) {
    if (editingStatus === EDITING_STATUS.ADD) {
      handleAddItem(item);
    } else if (editingStatus === EDITING_STATUS.EDIT) {
      handleUpdateItemList(item);
    }
  }

  return (
    <div className="row">
      <div className="container-fluid">
        <div className="row">
          <MainDisplay items={items} updateChanging={updateChanging} handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} />
          {editingStatus !== EDITING_STATUS.NONE ?
            <Editing status={editingStatus} item={editingItem} updateChanging={updateChanging} handleFinishEdit={handleFinishEdit} /> :
            <></>}
        </div>
      </div>
    </div>
  )
}