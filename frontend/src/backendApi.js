const API_ROOT = 'http://localhost:8080/api';

const REQUEST_OPTIONS = {
  method: "",
  headers: {
    'Content-Type': 'application/json',
  },
}


const requests = () => {
  let options = { ...REQUEST_OPTIONS, headers: { ...REQUEST_OPTIONS.headers } };

  return {
    del: url => {
      options = { ...options, method: "DELETE" };
      return fetch(`${API_ROOT}${url}`, options);
    },
    get: url => {
      options = { ...options, method: "GET" };
      return fetch(`${API_ROOT}${url}`, options);
    },
    put: (url, body) => {
      options = { ...options, method: "PUT" };
      options.body = JSON.stringify(body);
      return fetch(`${API_ROOT}${url}`, options);
    },
    post: (url, body) => {
      options = { ...options, method: "POST" };
      options.body = JSON.stringify(body);
      return fetch(`${API_ROOT}${url}`, options);
    }
  }
}

const Item = {
  getAll: () => requests().get("/items").then(response => response.json()),
  get: (id) => requests().get(`/items/${id}`).then(response => response.json()),
  update: (item) => requests().put(`/items/${item.id}`, { "item": item }).then(response => { }),
  post: async (item) => requests().post('/items', { "item": item }).then(response => response.json()),
  delete: (id) => requests().del(`/items/${id}`)
}

const RoomPrice = {
  getAll: () => requests().get("/room-prices").then(response => response.json()),
  update: (item) => requests().put(`/room-prices/${item.id}`, { "roomPrice": item }).then(response => response.json()),
  post: (item) => requests().post('/room-prices', { "roomPrice": item }).then(response => response.json()),
  delete: (id) => requests().del(`/room-prices/${id}`)
}

const RoomType = {
  getAll: () => requests().get("/room-types").then(response => response.json()),
  update: (item) => requests().put(`/room-types/${item.id}`, { "roomType": item }).then(response => { }),
  post: (item) => requests().post('/room-types', { "roomType": item }).then(response => response.json()),
  delete: (id) => requests().del(`/room-types/${id}`)
}

const HouseType = {
  getAll: () => requests().get("/house-types").then(response => response.json()),
  update: (item) => requests().put(`/house-types/${item.id}`, { "houseType": item }).then(response => { }),
  post: (item) => requests().post('/house-types', { "houseType": item }).then(response => response.json()),
  delete: (id) => requests().del(`/house-types/${id}`)
}

const RoomConfig = {
  getAll: () => requests().get("/room-configs").then(response => response.json()),
  update: (item) => requests().put(`/room-configs/${item.id}`, { "roomConfig": item }),
  post: (item) => requests().post('/room-configs', { "roomConfig": item }).then(response => response.json()),
  delete: (id) => requests().del(`/room-configs/${id}`)
}

const Room = {
  getAll: () => requests().get("/rooms").then(response => response.json()),
  getRoom: (fullName) => requests().get(`/rooms/${fullName}`).then(response => response.json()),
  update: (fullName, body) => requests().put(`/rooms/${fullName}`, { "room": body }).then(response => response.json()),
  checkIn: (fullName) => requests().post(`/rooms/${fullName}/check-in`).then(response => response.json()),
  checkout: (fullName) => requests().post(`/rooms/${fullName}/check-out`).then(response => response.json()),
}

const Order = {
  getAll: (fullName) => requests().get(`/rooms/${fullName}/items`).then(response => response.json()),
  update: (fullName, item) => requests().put(`/rooms/${fullName}/items/${item.id}`, {"item": item}),
  post: (fullName, item) => requests().post(`/rooms/${fullName}/items`, {"item": item}).then(response => response.json()),
  delete: (fullName, id) => requests().del(`/rooms/${fullName}/items/${id}`)
}

const Customer = {
  get: (fullName) => requests().get(`/rooms/${fullName}/customer`).then(response => response.json()),
  update: (fullName, item) => requests().put(`/rooms/${fullName}/customer`, {"customer": item}),
  post: (fullName, item) => requests().post(`/rooms/${fullName}/customer`, {"customer": item}).then(response => response.json()),
  delete: (fullName, id) => requests().del(`/rooms/${fullName}/customer/${id}`)
}

let limit = (count, p) => `limit=${count}&offset=${p}`;

const RoomHistory = {
  get: (params, page) => requests().get(`/history/rooms?${limit(10, page)}&houseType=${params.houseType}&date=${params.date}`).then(response => response.json())
}

const OrderHistory = {
  get: (id) => requests().get(`/history/${id}/orders`).then(res => res.json())
}


const CustomerHistory = {
  get: (id) => requests().get(`/history/${id}/customer`).then(res => res.json())
}

const exportedObject = {
  Item,
  RoomConfig,
  RoomPrice,
  RoomType,
  HouseType,
  Room,
  Order,
  Customer,
  RoomHistory,
  CustomerHistory,
  OrderHistory
};

export default exportedObject;