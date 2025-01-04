import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "../../css/BoardDetail.css";

function BoardDetail() {
    const { seqBoardNum } = useParams(); // 게시글 번호 파라미터 받기
    const navigate = useNavigate();

    // 게시글 데이터를 관리하는 상태
    const [post, setPost] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // 게시글 데이터 로드 (API 호출)
    useEffect(() => {
        // 데이터를 가져오는 함수 정의
        const fetchPost = async () => {
            try {
                const response = await axios.get(`/board/${seqBoardNum}`);
                setPost(response.data.board); // 컨트롤러에서 반환한 데이터를 상태로 설정
            } catch (err) {
                setError(err.message || "Failed to fetch data");
            } finally {
                setLoading(false); // 로딩 상태 업데이트
            }
        };

        fetchPost();
    }, [seqBoardNum]);

    const handleEditClick = () => {
        navigate(`/board/write/${post.seqBoardNum}`); // 수정 페이지로 이동
    };

    const handleDeleteClick = () => {
        if (window.confirm("정말 삭제하시겠습니까?")) {
            console.log("게시글 삭제:", post.seqBoardNum);
            navigate("/board/list"); // 삭제 후 목록으로 돌아가기
        }
    };

    const handleBackToList = () => {
        navigate("/board/list"); // 목록으로 돌아가기
    };

    if (loading) return <p>Loading...</p>; // 로딩 중인 경우 표시
    if (error) return <p>Error: {error}</p>; // 오류가 발생한 경우 표시
    if (!post) return <p>No post found</p>; // 데이터가 없는 경우 표시

    return (
        <div>
            <div className="post-detail-container">
                <div className="post-header">
                    <div className="actions">
                        <button onClick={handleEditClick} className="btn-small">수정</button>
                        <button onClick={handleDeleteClick} className="btn-secondary">삭제</button>
                    </div>
                    <div>
                        <h1>{post.title}</h1>
                        <p className="post-meta">
                            작성자: {post.name} | 작성일: {new Date(post.createDt).toLocaleString()}
                        </p>
                    </div>
                </div>
                <div className="post-content">
                    <p>{post.content}</p>
                </div>
            </div>
            <div className="btn-back-container">
                <div className="actions">
                    <button onClick={handleBackToList} className="btn-back">목록으로</button>
                </div>
            </div>
        </div>
    );
}

            export default BoardDetail;