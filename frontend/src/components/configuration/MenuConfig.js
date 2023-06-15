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
                  <th scope="col">Giá</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                {items.map((drink, index) => <Item key={index} index={index+1} item={drink} handleEditClick={_handleEditClick} handleDeleteClick={handleDeleteClick}/>)}
              </tbody>
            </table>
      </div>
    </div>
  )
}

function Editing({status, item, updateChanging, handleFinishEdit}) {
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');

  useEffect(()=>{
    setName(item.name);
    setPrice(item.price);
  }, [item.name, item.price])

  function handleEditClick() {
    let newItem = item;
    if(name !== '') {
      newItem.name = name;
    }
    if(price !== '') {
      newItem.price = price;
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

  const handlePriceChange = (event) => {
    setPrice(event.target.value);
  }

  return(
    <div className="col-4">
        <form className="row g-3">
            <div className="col-md-6">
              <label htmlFor="itemName" className="form-label">Tên sản phẩm</label>
              <input type="" className="form-control" id="itemName" value={name} onChange={handleNameChange}/>
            </div>
            <div className="col-md-6">
              <label htmlFor="itemPrice" className="form-label">Giá</label>
              <input type="" className="form-control" id="itemPrice" value={price} onChange={handlePriceChange}/>
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
  return (
    <tr>
      <th scope="row">{index}</th>
      <td>{item.name}</td>
      <td>{item.price}</td>
      <td>
        <button type="button" className="btn btn-primary" onClick={() => handleEditClick(item)}>Sửa</button>
        <button type="button" className="btn btn-danger" onClick={() => handleDeleteClick(item)}>Xóa</button>
      </td>
    </tr>
  )
}



export default function MenuConfig() {
  const [editingStatus, setEditingStatus] = useState(EDITING_STATUS.NONE);
  const [items, updateItems] = useState([]);
  const [editingItem, setEditingItem] = useState({name: '', price: ''})

  useEffect(() => {
    backendApi.Item.getAll().then(data => updateItems(data.items)).catch(error => {});

    return () => {};
  }, [])

  function updateChanging(status) {
    if(status === EDITING_STATUS.NONE) {
      setEditingItem({name: '', price: ''})
    }
    setEditingStatus(status);
  }

  function handleEditClick(item) {
    setEditingItem(item)
  }

  function handleDeleteClick(item) {
    backendApi.Item.delete(item.id);
    let newItems = items.filter(it => it.id !== item.id);
    updateItems(newItems);

  }

  function handleUpdateItemList(item) {
    let newItems = items.map(it => {
      if(item.id === it.id) {
        it.name = item.name;
        it.price = item.price;
      }
      return it;
    })
    backendApi.Item.update(item);
    updateItems(newItems);
  }


  function handleAddItem(item) {
    backendApi.Item.post(item).then(data => {
      let newItems = [...items, data.item];
      updateItems(newItems);
    });
    
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