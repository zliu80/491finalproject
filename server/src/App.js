import Layout from './components/Layout'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

import Home from './pages/Home'
import Dashboard from './pages/Dashboard'
import Users from './pages/Users'
import Attractions from './pages/Attractions'
import Login from './pages/Login'

function App() {
    return (
        <BrowserRouter>
            <Layout>
                <Routes>
                    <Route path='/' element={<Login />} />
                    <Route path='/dashboard' element={<Dashboard />} />
                    <Route path='/users' element={<Users />} />
                    <Route path='/attractions' element={<Attractions />} />
                    <Route path='/login' element={<Login />} />
                    <Route path='*' element={<Home/>}/>
                </Routes>
            </Layout>
        </BrowserRouter>
    )
}

export default App