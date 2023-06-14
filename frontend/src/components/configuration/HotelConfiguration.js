import './Config.css'
import { useNavigate } from "react-router-dom"

const configItems = [
    {
        'image': "./assert/door-svgrepo-com.svg",
        'alt': 'room',
        'name': "Phòng",
        'url': '/room'
    },
    {
        'image': "./assert/cup-straw.svg",
        'alt': 'drinks',
        'name': "Đồ uống",
        'url': '/drinks'
    },
    {
        'image': "./assert/bunk-bed-svgrepo-com.svg",
        'alt': 'roomType',
        'name': "Kiểu phòng",
        'url': '/roomType'
    },
    {
        'image': "./assert/house.svg",
        'alt': 'houseType',
        'name': "Kiểu nhà",
        'url': '/houseType'
    },
    {
        'image': "./assert/cash-coin.svg",
        'alt': 'charge',
        'name': "Tính tiền",
        'url': '/charging'
    },
]

function ConfigItem({item}) {
  let navigate = useNavigate();


  return (
      <div className="col col-sm-6 col-md-4 mb-4 min-wh">
          <div className="card" onClick={() => {
            navigate(window.location.pathname+item.url)
          }}>
              <div className="card-body">
                  <div className="d-flex flex-column justify-content-center align-items-center">
                      <img src={item.image} alt={item.alt} width="50" height="50"/>
                      <h1 className='user-select-none'>{item.name}</h1>
                  </div>
              </div>
          </div>
      </div>
  )
}


export default function HotelConfiguration() {
  return (
    <div className="row">
        <div className="container-fluid">
            <div className="row">
                <div className="col-8">
                    <div className="container-fluid">
                        <div className="row">
                            { configItems.map((configItem, index) => <ConfigItem key={index} item={configItem} />) }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}