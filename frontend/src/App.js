import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import HotelConfiguration from './components/configuration/HotelConfiguration';
import MenuConfig from './components/configuration/MenuConfig';
import RoomType from './components/configuration/RoomType';
import HouseType from './components/configuration/HouseType';
import RoomPrice from './components/configuration/RoomPrice';
import RoomConfig from './components/configuration/RoomConfig';
import Renting from './components/operating/Renting';
import HotelHistory from './components/hotelHistory/HotelHistory';

function App() {
  return ( 
    <BrowserRouter>
      <div className="container-fluid">
        <div className="row gx-0">
          <Sidebar />
          <div className="col-xl-10 col-lg-9 col-md-8">
            <div className="container-fluid mx-0">
              <Navbar />
              <Routes>
                <Route path='/' element={<Renting/>}> </Route>
                <Route path='/config' element={<HotelConfiguration/>}> </Route>
                <Route path='/config/drinks' element={<MenuConfig/>}> </Route>
                <Route path='/config/roomtype' element={<RoomType/>}> </Route>
                <Route path='/config/housetype' element={<HouseType/>}> </Route>
                <Route path='/config/charging' element={<RoomPrice/>}> </Route>
                <Route path='/config/room' element={<RoomConfig/>}> </Route>
                <Route path='/history' element={<HotelHistory/>}> </Route>
              </Routes>
            </div>
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
