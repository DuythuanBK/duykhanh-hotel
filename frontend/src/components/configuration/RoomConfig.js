import { useEffect, useState } from "react"
import backendApi from "../../backendApi";

const EDITING_STATUS = {
  NONE: 0,
  ADD: 1,
  EDIT: 2
}

function MainDisplay({items, houseTypes, 
                      updateChanging, handleEditClick, 
                      handleDeleteClick}) {
  
  const [choosedHouseType, setChoosedHouseType] = useState('Nhà cũ');
  
  useEffect(() => {
  }, [])

  const handleChoosedHousetypeChanged = (e) => {
    setChoosedHouseType(e.target.value);
  } 

  function _handleAddClick() {
    updateChanging(EDITING_STATUS.ADD);
  }

  function _handleEditClick(item) {
    updateChanging(EDITING_STATUS.EDIT);
    handleEditClick(item);
  }

  return (
    <div className="col-8">
    <div className="d-flex">
      <button type="button" className="btn btn-primary" onClick={_handleAddClick}>Thêm</button>
      <select className="ms-5 me-5 w-25 form-select" id="roomtype" onChange={handleChoosedHousetypeChanged}>
      {
        houseTypes.map((item, index) => <option key={index} value={item.name}>{item.name}</option>)
      }
      </select>
      </div>
      <div className="overflow-scroll" style={{height: '90vh'}}>
          <table className="table">
              <thead>
                <tr>
                  <th scope="col">Thứ tự</th>
                  <th scope="col">Loại phòng</th>
                  <th scope="col">Kiểu nhà</th>
                  <th scope="col">Giá phòng thường</th>
                  <th scope="col">Giá phòng điều hòa</th>
                  <th scope="col">Tên phòng</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                {
    
                  items.filter(it => it.houseType === choosedHouseType).map((item, index) => <Item key={index} index={index+1} item={item} handleEditClick={_handleEditClick} handleDeleteClick={handleDeleteClick}/>) 

                }
              </tbody>
            </table>
      </div>
    </div>
  )
}

function Editing({status, item, roomPriceList, roomTypeList, 
                  houseTypeList, updateChanging, handleFinishEdit}) {
  const [name, setName] = useState(status === EDITING_STATUS.EDIT ? item.name : '');
  const [roomType, setRoomType] = useState(status === EDITING_STATUS.EDIT ? item.roomType : '');
  const [houseType, setHouseType] = useState(status === EDITING_STATUS.EDIT ? item.houseType : '');
  const [normalPrice, setNormalPrice] = useState(status === EDITING_STATUS.EDIT ? item.normalPrice : '');
  const [coldPrice, setColdPrice] = useState(status === EDITING_STATUS.EDIT ? item.coldPrice : '');


  useEffect(()=>{
    if(status === EDITING_STATUS.ADD) {
      setRoomType(roomTypeList[0].name);
      setNormalPrice(roomPriceList[0].name);
      setColdPrice(roomPriceList[0].name);
      setHouseType(houseTypeList[0].name);
    }
  }, [status, roomTypeList, roomPriceList, houseTypeList])

  function validateInput() {
    if(name === "" || name === undefined) {
      return false;
    }

    return true;
  }

  function handleEditClick() {
    if(validateInput()) {
      let newItem = item;
      newItem.name = name;
      newItem.roomType = roomType;
      newItem.houseType = houseType;
      newItem.normalPrice = normalPrice;
      newItem.coldPrice = coldPrice;
  
      handleFinishEdit(newItem);
      updateChanging(EDITING_STATUS.NONE);
    } else {

    }
    
  }

  function handleCancelClick() {
    updateChanging(EDITING_STATUS.NONE);
  }

  const handleNameChange = (event) => {
    setName(event.target.value);
  }

  const handleRoomTypeChange = (event) => {
    setRoomType(event.target.value);
  }

  const handleHouseTypeChange = (event) => {
    setHouseType(event.target.value);
  }

  const handleNormalPriceChange = (event) => {
    setNormalPrice(event.target.value);
  }

  const handleColdPriceChange = (event) => {
    setColdPrice(event.target.value);
  }

  return(
    <div className="col-4">
        <form className="row g-3">
            <div className="col-md-6">
              <label htmlFor="name" className="form-label">Tên</label>
              <input type="" className="form-control" id="name" value={name} onChange={handleNameChange}/>
            </div>
            <div className="col-md-6">
              <label htmlFor="roomtype" className="form-label">Loại phòng</label>
              <select className="form-select" id="roomtype" onChange={handleRoomTypeChange}>
                {
                  roomTypeList.map((item, index) => <option key={index} value={item.name}>{item.name}</option>)
                }
              </select>
            </div>
            <div className="col-md-6">
              <label htmlFor="housetype" className="form-label">Kiểu nhà</label>
              <select className="form-select" id="housetype" onChange={handleHouseTypeChange}>
                {
                  houseTypeList.map((item, index)  => <option key={index} value={item.name}>{item.name}</option>)
                }
              </select>
            </div>
            <div className="col-md-6">
              <label htmlFor="normalPrice" className="form-label">Giá phòng thường</label>
              <select className="form-select" id="normalPrice" onChange={handleNormalPriceChange}>
                {
                  roomPriceList.filter(it => !it.name.includes("ĐH")).map((item, index)  => <option key={index} value={item.name}>{item.name}</option>)
                }
              </select>
            </div>
            <div className="col-md-6">
              <label htmlFor="coldPrice" className="form-label">Giá phòng điều hòa</label>
              <select className="form-select" id="coldPrice" onChange={handleColdPriceChange}>
                {
                  roomPriceList.filter(it => it.name.includes("ĐH")).map((item, index)  => <option key={index} value={item.name}>{item.name}</option>)
                }
              </select>
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

  function _handleEditClick(item) {
    handleEditClick(item);
  }

  const _handleDeleteClick = (item) => {
    handleDeleteClick(item);
  }

  return (
    <tr>
      <th scope="row">{index}</th>
      <td>{item.roomType}</td>
      <td>{item.houseType}</td>
      <td>{item.normalPrice}</td>
      <td>{item.coldPrice}</td>
      <td>{item.name}</td>
      <td>
        <button type="button" className="btn btn-primary" onClick={() => _handleEditClick(item)}>Sửa</button>
        <button type="button" className="btn btn-danger" onClick={() => _handleDeleteClick(item)}>Xóa</button>
      </td>
    </tr>
  )
}

export default function RoomConfig() {
  const [editingStatus, setEditingStatus] = useState(EDITING_STATUS.NONE);
  const [items, updateItems] = useState([]);
  const [editingItem, setEditingItem] = useState({});
  const [roomPrices, setRoomPrices] = useState([]);
  const [roomTypes, setRoomTypes] = useState([]);
  const [houseTypes, setHouseTypes] = useState([]);

  useEffect(() => {
    try {
      backendApi.RoomConfig.getAll().then(data => updateItems(data.roomConfigs));
      backendApi.HouseType.getAll().then(data => setHouseTypes(data.houseTypes));
      backendApi.RoomType.getAll().then(data => setRoomTypes(data.roomTypes));
      backendApi.RoomPrice.getAll().then(data => setRoomPrices(data.roomPrices));
      
    } catch(e) {

    }
  }, []);

  function updateChanging(status) {
    setEditingStatus(status);
  }

  function handleEditClick(item) {
    setEditingItem(item)
  }

  function handleDeleteClick(item) {
    backendApi.RoomConfig.delete(item);
    let newItems = items.filter(it => it.id !== item.id);
    updateItems(newItems);
  }

  function handleUpdateItemList(item) {
    backendApi.RoomConfig.update(item);
    let newItems = items.map(it => {
      if(item.id === it.id) {
        it.name = item.name;
      }
      return it;
    })

    updateItems(newItems);
  }

  function handleAddItem(item) {
    backendApi.RoomConfig.post(item).then(data => {
      updateItems([...items, data.roomConfig]);
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
          <MainDisplay items={items} houseTypes={houseTypes} updateChanging={updateChanging}  
            handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick}
          />
          { editingStatus !== EDITING_STATUS.NONE ? 
            <Editing  status={editingStatus} 
                      item={editingItem} 
                      roomPriceList={roomPrices}
                      roomTypeList={roomTypes}
                      houseTypeList={houseTypes}
                      updateChanging={updateChanging} 
                      handleFinishEdit={handleFinishEdit} /> : 
            <></> }
        </div>
      </div>
    </div>
  )
}