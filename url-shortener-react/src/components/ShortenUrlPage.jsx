import React, { useEffect } from 'react'
import { useParams } from 'react-router-dom';

const ShortenUrlPage = () => {
    const {url}=useParams();
    console.log("url",url);
    useEffect(()=>{
         if(url){
          console.log("url",url)
            window.location.href=import.meta.env.VITE_BACKEND_URL +"/"+ `${url}`
         }
    },[url])
  return null
}

export default ShortenUrlPage