import { useEffect, useState } from "react"
import backendApi from "../../backendApi";


const EDITING_STATUS = {
  NONE: 0,
  ADD: 1,
  EDIT: 2
}

function MainDisplay({items, updateChanging, handleEditClick, handleDeleteClick}) {
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
      <div className="overflow-scroll" style={{height: '90vh'}}>
          <table className="table">
              <thead>
                <tr>
                  <th scope="col">Thứ tự</th>
                  <th scope="col">Tên</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                {items.map((item, index) => <Item key={index} index={index+1} item={item} handleEditClick={_handleEditClick} handleDeleteClick={handleDeleteClick}/>)}
              </tbody>
            </table>
      </div>
    </div>
  )
}

function Editing({status, item, updateChanging, handleFinishEdit}) {
  const [name, setName] = useState('');

  useEffect(()=>{
    if(status === EDITING_STATUS.EDIT) {
      setName(item.name);
    }
  }, [item.name, status])

  function handleEditClick() {
    let newItem = item;
    if(name !== '') {
      newItem.name = name;
    }

    handleFinishEdit(newItem);
    updateChanging(EDITING_STATUS.NONE);
  }

  function handleCancelClick() {
    updateChanging(EDITING_STATUS.NONE);
  }

  const handleNameChange = (event) => {
    setName(event.target.value);
  }

  return(
    <div className="col-4">
        <form className="row g-3">
            <div className="col-md-6">
              <label htmlFor="name" className="form-label">Kiểu nhà</label>
              <input type="" className="form-control" id="name" value={name} onChange={handleNameChange}/>
            </div>
            <div className="col-md-12 d-flex justify-content-center">
                <button type="button" className="btn btn-primary me-1 w-25" onClick={handleEditClick}>{status === EDITING_STATUS.ADD ? 'Thêm' : 'Sửa'}</button>
                <button type="button" className="btn btn-primary ms-1 w-25" onClick={handleCancelClick}>Hủy</button>
            </div>
        </form>
    </div>
  )
}

function Item({index, item, handleEditClick, handleDeleteClick}){

  function _handleOnClick(item) {
    handleEditClick(item);
  }

  return (
    <tr>
      <th scope="row">{index}</th>
      <td>{item.name}</td>
      <td>
        <button type="button" className="btn btn-primary" onClick={() => _handleOnClick(item)}>Sửa</button>
        <button type="button" className="btn btn-danger" onClick={() => handleDeleteClick(item)}>Xóa</button>

      </td>
    </tr>
  )
}



export default function HouseType() {
  const [editingStatus, setEditingStatus] = useState(EDITING_STATUS.NONE);
  const [items, updateItems] = useState([]);
  const [editingItem, setEditingItem] = useState({name: ''})

  useEffect(() => {
    backendApi.HouseType.getAll().then(data => {
      if('houseTypes' in data) {
        updateItems(data.houseTypes);
      }
    }).catch(error => {})
    }, []);

  function updateChanging(status) {
    if(status === EDITING_STATUS.NONE) {
      setEditingItem({name: ''})
    }
    setEditingStatus(status);
  }

  function handleEditClick(item) {
    setEditingItem(item)
  }

  const handleDeleteClick = (item) => {
    backendApi.HouseType.delete(item.id);
    let newItems = items.filter(it => it.id !== item.id);
    updateItems(newItems);
  }

  function handleUpdateItemList(item) {
    backendApi.HouseType.update(item);
    let newItems = items.map(it => {
      if(item.id === it.id) {
        it.name = item.name;
      }
      return it;
    })

    updateItems(newItems);
  }

  function handleAddItem(item) {
    backendApi.HouseType.post(item);
    let newItems = items;
    newItems.push(item);
    updateItems(items);
  }

  function handleFinishEdit(item) {
    if(editingStatus === EDITING_STATUS.ADD) {
      handleAddItem(item);
    } else if(editingStatus === EDITING_STATUS.EDIT) {
      handleUpdateItemList(item);
    }
  }

  return (
    <div className="row">
      <div className="container-fluid">
        <div className="row">
          <MainDisplay items={items} updateChanging={updateChanging} handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick}/>
          { editingStatus !== EDITING_STATUS.NONE ? 
            <Editing status={editingStatus} item={editingItem} updateChanging={updateChanging} handleFinishEdit={handleFinishEdit} /> : 
            <></> }
        </div>
      </div>
    </div>
  )
}