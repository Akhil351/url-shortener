import React, { useState } from 'react'
import { Tooltip } from '@mui/material';
import { RxCross2 } from 'react-icons/rx';
import { useStoreContext } from '../../contextApi/ContextApi';
import toast from 'react-hot-toast';
import api from '../../api/api';
const CreateNewShorten = ({ setOpen, refetch }) => {
    const { token } = useStoreContext();
    const [loading, setLoading] = useState(false);
    const [formData, setFormData] = useState({ originalUrl: '' });
    const [errors, setErrors] = useState({});
    const urlRegex =
        /^(https?:\/\/)?(([a-zA-Z0-9\u00a1-\uffff-]+\.)+[a-zA-Z\u00a1-\uffff]{2,})(:\d{2,5})?(\/[^\s]*)?$/;
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
        setErrors({ ...errors, [name]: '' });
    };
    const validateForm = () => {
        const newErrors = {};
        if (!formData.originalUrl.trim()) {
            newErrors.originalUrl = 'URL is required';
        } else if (!urlRegex.test(formData.originalUrl)) {
            newErrors.originalUrl = 'Please enter a valid URL';
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };
    const createShortUrlHandler = async (e) => {
        e.preventDefault();
        if (!validateForm()) return;

        setLoading(true);
        try {
            const respone = await api.post("/api/urls/shorten", formData, {
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "Authorization": `Bearer ${token}`
                },
            });
            const shortUrl = respone.data.data.shortUrl
            const shortenUrl = `${import.meta.env.VITE_REACT_SUBDOMAIN}/${shortUrl}`;
            navigator.clipboard.writeText(shortenUrl).then(() => {
                toast.success("Short Url Copied to ClipBoard", {
                    position: "top-center",
                    className: "mb-5",
                    duration: 3000
                })
            })
            console.log(respone.data.data)
     //       await refetch()
            setFormData({ originalUrl: '' });
            setOpen(false)

        }
        catch (error) {
            console.log(error)
            toast.error("Create Short Url Failed")
        }
        finally {
            setLoading(false)
        }

    };
    return (
        <div className="flex justify-center items-center bg-white rounded-md">
            <form
                onSubmit={createShortUrlHandler}
                className="sm:w-[450px] w-[360px] relative shadow-custom pt-8 pb-5 sm:px-8 px-4 rounded-lg"
            >
                <h1 className="font-montserrat sm:mt-0 mt-3 text-center font-bold sm:text-2xl text-[22px] text-slate-800">
                    Create New Shorten URL
                </h1>

                <hr className="mt-2 sm:mb-5 mb-3 text-slate-950" />

                <div className="flex flex-col gap-1">
                    <label htmlFor="originalUrl" className="font-semibold text-md">
                        Enter URL
                    </label>
                    <input
                        type="url"
                        name="originalUrl"
                        id="originalUrl"
                        value={formData.originalUrl}
                        onChange={handleChange}
                        placeholder="https://example.com"
                        className={`px-2 py-2 border outline-none bg-transparent text-slate-700 rounded-md ${errors.originalUrl ? 'border-red-500' : 'border-slate-600'
                            }`}
                    />
                    {errors.originalUrl && (
                        <p className="text-sm font-semibold text-red-600 mt-0">
                            {errors.originalUrl}*
                        </p>
                    )}
                </div>

                <button
                    type="submit"
                    disabled={loading}
                    className="bg-customRed font-semibold text-white bg-custom-gradient w-full py-2 hover:text-slate-400 transition-colors duration-100 rounded-sm my-3"
                >
                    {loading ? 'Loading...' : 'Create'}
                </button>

                {!loading && (
                    <Tooltip title="Close">
                        <button
                            type="button"
                            onClick={() => setOpen(false)}
                            className="absolute right-2 top-2"
                        >
                            <RxCross2 className="text-slate-800 text-3xl" />
                        </button>
                    </Tooltip>
                )}
            </form>
        </div>
    )
}

export default CreateNewShorten