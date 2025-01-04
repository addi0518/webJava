import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../css/Login.css";

function Home() {
    const [user, setUser] = useState(null); // 사용자 정보 상태
    const [error, setError] = useState(null); // 에러 상태
    const [loading, setLoading] = useState(true); // 로딩 상태
    const navigate = useNavigate();

    // 회원가입 페이지로 이동
    const handleJoinClick = () => {
        navigate("/join");
    };

    // 로그아웃 처리
    const handleLogout = () => {
        localStorage.removeItem("token"); // 토큰 삭제
        setUser(null); // 사용자 정보 초기화
    };

    // 사용자 정보 가져오기
    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem("token"); // 토큰 가져오기

            if (!token) {
                setLoading(false); // 토큰이 없으면 로딩 종료
                return;
            }

            try {
                const response = await axios.get("/", {
                    headers: {
                        Authorization: `Bearer ${token}`, // 토큰을 헤더에 추가
                    },
                });

                const { username, role, UserEntity } = response.data;
                setUser({
                    email: username,
                    role: role,
                    userEntity: UserEntity,
                });
            } catch (err) {
                setError("사용자 정보를 가져오지 못했습니다.");
            } finally {
                setLoading(false);
            }
        };

        fetchUser();
    }, []);

    // 로딩 상태 표시
    if (loading) return <div>Loading...</div>;

    // 에러 상태 표시
    if (error) return <div>Error: {error}</div>;

    return (
        <div>
            {!user ? ( // 로그인 상태에 따라 UI 변경
                <div className="login-div-container">
                    <div className="login-container">
                        <h2>로그인</h2>
                        <form
                            onSubmit={async (e) => {
                                e.preventDefault();
                                const formData = new FormData(e.target);
                                const username = formData.get("username");
                                const password = formData.get("password");

                                try {
                                    const response = await axios.post("/login", {
                                        username,
                                        password,
                                    });

                                    const token = response.data.token; // 로그인 성공 시 JWT 토큰 저장
                                    localStorage.setItem("token", token);
                                    setUser(response.data.user); // 사용자 정보 설정
                                } catch (err) {
                                    setError("로그인 실패!");
                                }
                            }}
                        >
                            <input
                                type="text"
                                name="username"
                                placeholder="이메일"
                                required
                            />
                            <input
                                type="password"
                                name="password"
                                placeholder="비밀번호"
                                required
                            />
                            <button type="submit">로그인</button>
                        </form>
                        <button className="signup-button" onClick={handleJoinClick}>
                            회원가입
                        </button>
                    </div>
                </div>
            ) : (
                <div style={{ textAlign: "center", marginTop: "20px" }}>
                    <h2>환영합니다, {user.email}</h2>
                    <p>Role: {user.role}</p>
                    <p>UserEntity: {JSON.stringify(user.userEntity)}</p>
                    <button className="logout-button" onClick={handleLogout}>
                        로그아웃
                    </button>
                </div>
            )}
        </div>
    );
}

export default Home;