import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import './global.css';
import StartForm from './components/StartForm.jsx'
import LoginForm from './components/safety/LoginForm.jsx'
import Signup from './components/safety/Signup.jsx'
import Main from './components/Main.jsx'
import Walking from './components/Walking.jsx'
import MyPage from './components/MyPage.jsx'
import WalkDetail from './components/WalkDetail.jsx'

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<StartForm/>}/> {/* 最初 */}
                <Route path="/login" element={<LoginForm/>}/> {/* ログイン */}
                <Route path="/signup" element={<Signup/>}/> {/* 新規作成 */}
                <Route path="/main" element={<Main/>}/> {/* 散歩スタート画面と今日の履歴 */}
                <Route path="/walk" element={<Walking/>}/> {/* 最初 */}
                <Route path="/myPage" element={<MyPage/>}/> {/* 散歩中 */}
                <Route path="/walks/:id" element={<WalkDetail/>}/> {/* 過去の散歩画面 */}
            </Routes>
        </BrowserRouter>
    </StrictMode>
)
