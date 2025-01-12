
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import './App.css'
import { LandingPage } from './components/LandingPage'
import AboutPage from './components/AboutPage'
import { NavBar } from './components/NavBar'
import { Footer } from './components/Footer'
import RegisterPage from './components/RegisterPage'
import { Toaster } from 'react-hot-toast'
import Login from './components/Login'


function App() {
  return (
    <>
      <Router>
        <NavBar />
        <Toaster position='top-center'  />
        <Routes>
          <Route path='/' element={<LandingPage />} />
          <Route path='/about' element={<AboutPage />} />
          <Route path='/register' element={<RegisterPage />} />
          <Route path='/login' element={<Login />} />
        </Routes>
        <Footer />
      </Router>
    </>
  )
}

export default App
