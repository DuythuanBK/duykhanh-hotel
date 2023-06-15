import { useEffect, useState } from "react";
import backendApi from "../../backendApi";


const STATUS = {
  NONE: 0,
  EDIT: 1
}


export default function CustomerInformation({room}) {
  const [status, setStatus] = useState(STATUS.NONE);
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [dateOfBirth, setDateOfBirth] = useState('');
  const [address, setPlaceOfBirth] = useState('');
  const [idNumber, setIdNumber] = useState('');
  const [numberplate, setNumberplate] = useState('');

  useEffect(() => {
    backendApi.Customer.get(room.fullName).then(data => {
      const customer = data.customer;

      if(customer.firstName !== null) {
        setFirstName(customer.firstName);
      }

      if(customer.lastName !== null) {
        setLastName(customer.lastName);
      }

      if(customer.dateOfBirth !== null) {
        setDateOfBirth(customer.dateOfBirth);
      }

      if(customer.address !== null) {
        setPlaceOfBirth(customer.address);
      }

      if(customer.idNumber !== null) {
        setIdNumber(customer.idNumber);
      }

      if(customer.numberplate !==null) {
        setNumberplate(customer.numberplate);
      }
    }).catch(error => {
      
    })
  }, [room.fullName])

  const handleChangeClicked = () => {
    if(status === STATUS.NONE) {
      setStatus(STATUS.EDIT);
    } else {
      const customer = {
        firstName: firstName,
        lastName: lastName,
        dateOfBirth: dateOfBirth,
        address: address,
        idNumber: idNumber,
        numberplate: numberplate
      }

      backendApi.Customer.update(room.fullName, customer);
      setStatus(STATUS.NONE);
    }
  }

  const handleFirstNameChanged = (e) => {
    setFirstName(e.target.value);
  }

  const handleLastNameChanged = (e) => {
    setLastName(e.target.value);
  }

  const handleDateOfBirthChanged = (e) => {
    setDateOfBirth(e.target.value);
  }

  const handlePlaceOfBirthChanged = (e) => {
    setPlaceOfBirth(e.target.value);
  }

  const handleIdNumberChanged = (e) => {
    setIdNumber(e.target.value);
  }

  const handleNumberplateChanged = (e) => {
    setNumberplate(e.target.value);
  }


  return (
    <>
      <div className="col-12">
        <div className="d-flex align-items-center justify-content-between">
          <p className="lead mb-0">Thông tin khách hàng</p>
          <button type="button" className="btn btn-secondary" onClick={handleChangeClicked}>
            { status === STATUS.NONE ? "Thay đổi" : "Xong" }</button>
        </div>
        <hr />
      </div>
      <div className="col-md-6 mt-0">
        <label htmlFor="firstname" className="form-label">Họ</label>
        {
          status === STATUS.NONE ?
          <input type="text" className="form-control" id="firstname" disabled
            value={firstName}
          />
          :
          <input type="text" className="form-control" id="firstname" 
            value={firstName} onChange={handleFirstNameChanged}
          />
        }
      </div>
      <div className="col-md-6 mt-0">
        <label htmlFor="lastname" className="form-label">Tên</label>
        {
          status === STATUS.NONE ?
          <input type="text" className="form-control" id="lastname" disabled
            value={lastName}
          />
          :
          <input type="text" className="form-control" id="lastname" 
            value={lastName}
            onChange={handleLastNameChanged}
          />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="dateOfBirth" className="form-label">Ngày sinh</label>
        {
          status === STATUS.NONE ?
          <input type="date" className="form-control" id="dateOfBirth" disabled
            value={dateOfBirth}
          />
          :
          <input type="date" className="form-control" id="dateOfBirth" 
            value={dateOfBirth}
            onChange={handleDateOfBirthChanged}
          />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="placeOfBirth" className="form-label">Nơi sinh</label>
        {
          status === STATUS.NONE ?
          <input type="text" className="form-control" id="placeOfBirth" disabled
            value={address}
          />
          :
          <input type="text" className="form-control" id="placeOfBirth" 
            value={address}
            onChange={handlePlaceOfBirthChanged}
          />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="idNumber" className="form-label">Căn cước</label>
        {
          status === STATUS.NONE ?
          <input type="number" className="form-control" id="idNumber" disabled
            value={idNumber}
          />
          :
          <input type="number" className="form-control" id="idNumber" 
            value={idNumber}
            onChange={handleIdNumberChanged}
          />
        }
      </div>
      <div className="col-md-6">
        <label htmlFor="numberplate" className="form-label">Biên số xe</label>
        {
          status === STATUS.NONE ?
          <input type="text" className="form-control" id="numberplate" disabled
            value={numberplate}
          />
          :
          <input type="text" className="form-control" id="numberplate" 
            value={numberplate}
            onChange={handleNumberplateChanged}
          />
        }
      </div>
    </>
  )
}
