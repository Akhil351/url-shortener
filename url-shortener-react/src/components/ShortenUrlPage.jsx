import React, { useEffect } from 'react'

const ShortenUrlPage = () => {
    const {url}=useParams();
    useEffect(()=>{
         if(url){
            window.location.href=import.meta.env.VITE_BACKEND_URL + `${url}`
         }
    },[])
  return null
}

export default ShortenUrlPage