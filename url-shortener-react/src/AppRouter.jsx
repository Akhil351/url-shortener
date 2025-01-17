import React from 'react'
import ShortenUrlPage from './components/ShortenUrlPage'
import { Routes, Route } from 'react-router-dom'
import { LandingPage } from './components/LandingPage'
import AboutPage from './components/AboutPage'
import { NavBar } from './components/NavBar'
import { Footer } from './components/Footer'
import RegisterPage from './components/RegisterPage'
import { Toaster } from 'react-hot-toast'
import LoginPage from './components/LoginPage'
import DashboardLayout from './components/Dashboard/DashboardLayout'
import PrivateRoute from './PrivateRoute'
import ErrorPage from './components/ErrorPage'
const AppRouter = () => {
  const hideHeaderFooter = location.pathname.startsWith("/s");
  return (
    <>
      { !hideHeaderFooter && <NavBar /> }
      <Toaster position='top-center' />
      <Routes>
        <Route path='/' element={<LandingPage />} />
        <Route path='/s/:url' element={<ShortenUrlPage />} />
        <Route path='/about' element={<AboutPage />} />
        <Route path='/register' element={<PrivateRoute publicPage={true}><RegisterPage /></PrivateRoute>} />
        <Route path='/login' element={<PrivateRoute publicPage={true}><LoginPage /></PrivateRoute>} />
        <Route path='/dashboard' element={<PrivateRoute publicPage={false}><DashboardLayout /></PrivateRoute>} />
        <Route path='*' element={<ErrorPage message=" We can't seem to find the page you're looking for " />} />
        <Route path='/error' element={<ErrorPage />} />
      </Routes>
      {!hideHeaderFooter && <Footer />}
    </>
  );
}

export default AppRouter