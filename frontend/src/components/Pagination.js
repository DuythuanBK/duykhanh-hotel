import { Link } from "react-router-dom"

export function Pagination({page, totalPage, handlePageChanged}) {

  let range = [];
  for(let i = 1; i <= totalPage; i++) {
    range.push(i);
  }

  return (
    <nav aria-label="Page navigation example">
      <ul className="pagination">
        {/* <li className="page-item">
          <Link className="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </Link>
        </li> */}
        {
          range.map((i, idx) => <li key={idx} className={page+1 === i ? "page-item active" : "page-item"}>
                            <Link className="page-link" href="" onClick={() => handlePageChanged(idx)}>{i}</Link>
                          </li>)
        }
        
        {/* <li class="page-item">
          <Link className="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </Link>
        </li> */}
      </ul>
    </nav>
  )
}