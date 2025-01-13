import { useQuery } from "react-query"
import api from '../api/api';
export const useFetchTotalClicks = (token, onError) => {
    return useQuery("url-totalclick"
        , async () => {
            return await api.get("/api/urls/totalClicks?startDate=2025-01-01&endDate=2025-12-31", {
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "Authorization": `Bearer ${token}`,
                }
            });
        }
        , {
           select: (data)=>{

           },
           onError,
           staleTime: 5000
        })
}