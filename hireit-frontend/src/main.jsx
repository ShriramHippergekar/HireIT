import React from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom'
import Home from './pages/Home'
import JobDetail from './pages/JobDetail'

function App(){
  return (
    <BrowserRouter>
      <div style={{padding:20}}>
        <h2>HireIT</h2>
        <nav><Link to='/'>Home</Link></nav>
        <Routes>
          <Route path='/' element={<Home/>} />
          <Route path='/jobs/:id' element={<JobDetail/>} />
        </Routes>
      </div>
    </BrowserRouter>
  )
}

createRoot(document.getElementById('root')).render(<App />)
