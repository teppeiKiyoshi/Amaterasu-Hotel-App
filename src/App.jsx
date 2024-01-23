import "../node_modules/bootstrap/dist/css/bootstrap.min.css"
import "/node_modules/bootstrap/dist/js/bootstrap.min.js"

import './App.css'
import AddRoom from './components/room/AddRoom'
import ExistingRoom from './components/room/ExistingRoom'

function App() {

  return (
    <>
      <AddRoom/>
      <ExistingRoom/>
    </>
  )
}

export default App
