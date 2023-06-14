export function CustomerHistory({ customer, handleCustomerModal }) {
  return (
    <div className="modal fade show d-block" id="customerModal" tabIndex="-1" role="dialog">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="customerModal">Thông tin khách hàng</h5>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"
              onClick={() => handleCustomerModal(false)} ></button>
          </div>
          <div className="modal-body">
            <div>
              <p>Họ: {customer.firstName}</p>
              <p>Tên: {customer.lastName}</p>
              <p>Địa chỉ: {customer.address}</p>
              <p>Ngày sinh: {customer.dateOfBirth}</p>
              <p>Cccd: {customer.idNumber}</p>
              <p>Biển số xe: {customer.numberplate}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}