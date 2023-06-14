function Item({item}) {
  return (
    <tr>
      <td>{item.name}</td>
      <td>{item.price}</td>
      <td>{item.quantity}</td>
      <td>{parseInt(item.price) * parseInt(item.quantity)}</td>
    </tr>
  )
}

export function OrderHistory({ orders, handleOrderModal }) {
  return (
    <div className="modal fade show d-block" id="orderModal" tabIndex="-1" role="dialog">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="orderModal">Thông tin đồ uống</h5>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" 
            onClick={() => handleOrderModal(false)}></button>
          </div>
          <div className="modal-body">
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
                  orders.map((item, index) => <Item key={index} item={item} />)
                }
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  )
}