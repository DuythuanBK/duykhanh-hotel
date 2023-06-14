import { Link } from "react-router-dom"
import './Sidebar.css'
import { useEffect, useState } from "react"

const SIDEBAR_ITEMS = [
	{
		id: 0,
		name: "CHO THUÊ PHÒNG",
		url: "/"
	},
	{
		id: 1,
		name: "QUẢN LÝ NN",
		url: "/config"
	},
	{
		id: 2,
		name: "LỊCH SỬ",
		url: "/history"
	},
	{
		id: 3,
		name: "THỐNG KÊ",
		url: "/statistic"
	},
	{
		id: 4,
		name: "KHO HÀNG",
		url: "/warehouse"
	},
]

function SidebarItem({selectedId, sidebarItem, handleSidebarItemClicked}) {

	function getClassNameForLink() {
		if(sidebarItem.id === selectedId) {
			return "nav-link link-dark active";
		} else {
			return "nav-link link-dark";
		}
	}

	return(
		<li className="nav-item">
			<Link to={sidebarItem.url} className={getClassNameForLink()}
				onClick={() => handleSidebarItemClicked(sidebarItem.id)}>
					<span>{sidebarItem.name}</span>
			</Link>
		</li>
	)	
}

export default function Sidebar() {
	const [selectedId, setSelectedId] = useState(SIDEBAR_ITEMS[0].id);

	useEffect(() => {
		const curPath = window.location.pathname;
		SIDEBAR_ITEMS.forEach(it => {
			if(curPath.includes(it.url)) {
				setSelectedId(it.id);
			}
		})
	}, [])

	const _handleSidebarItemClicked = (id) => {
		setSelectedId(id);
	}

	return (
		<div className="col-xl-2 col-lg-3 col-md-4">
			<div className="d-flex flex-column flex-shrink-0 p-3 bg-ligh vh-100">
				<Link href="/" className="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
						<h2 className="fs-4 text-danger">NHÀ NGHỈ DUY KHÁNH</h2>
				</Link>
				<hr />
				<ul className="nav nav-pills flex-column mb-auto">
					{
						SIDEBAR_ITEMS.map((item) => <SidebarItem key={item.id} selectedId={selectedId} sidebarItem={item} handleSidebarItemClicked={_handleSidebarItemClicked}/>)
					}
				</ul>
				{/* <hr />
				<div className="dropdown">
						<a href="/" className="d-flex align-items-center link-dark text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
								<img src="https://github.com/mdo.png" alt="" width="32" height="32" className="rounded-circle me-2" />
								<strong>admin</strong>
						</a>
						<ul className="dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
								<li><a className="dropdown-item" href="/">New project...</a></li>
								<li><a className="dropdown-item" href="/">Settings</a></li>
								<li><a className="dropdown-item" href="/">Profile</a></li>
								<li><hr className="dropdown-divider" /></li>
								<li><a className="dropdown-item" href="/">Sign out</a></li>
						</ul>
				</div> */}
			</div>
		</div>
	)
}