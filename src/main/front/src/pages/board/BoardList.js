import React, { useState, useEffect } from "react";
import { Table, Button, Form } from "react-bootstrap";
import "../../css/BoardList.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Link } from "react-router-dom";

function BoardList() {

    const navigate = useNavigate();

    const [boardList, setBoardList] = useState({
        content: [],
        hasPrevious: false,
        hasNext: false,
    });

    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    // 데이터를 가져오는 함수
    const fetchBoardList = async (page) => {
        try {
            const response = await axios.get("/board/list", {
                params: { page: page }, // Pageable 관련 파라미터
            });
            const data = response.data;
            setBoardList({
                content: data.boardList,
                hasPrevious: data.currentPage > 0,
                hasNext: data.currentPage < data.totalPages - 1,
            });
            setCurrentPage(data.currentPage);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error("Error fetching board list:", error);
        }
    };

    // 컴포넌트 마운트 시 데이터 로드
    useEffect(() => {
        fetchBoardList(currentPage);
    }, [currentPage]);

    const handleWriteClick = () => {
        navigate("/board/write");
    };

    const handleSearch = (keyword) => {
        console.log("검색 키워드:", keyword);
        // 검색 구현 추가
    };

    return (
        <div className="board-list-container">
            <h1 className="board-list-title">게시판</h1>
            <Table striped bordered hover className="board-list-table">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                </tr>
                </thead>
                <tbody>
                {boardList.content.map((post) => (
                    <tr key={post.seqBoardNum}>
                        <td>{post.seqBoardNum}</td>
                        <td>
                            <Link to={`/board/${post.seqBoardNum}`} className="board-link">
                                {post.title}
                            </Link>
                        </td>
                        <td>{post.name}</td>
                        <td>{new Date(post.createDt).toLocaleDateString("ko-KR")}</td>
                    </tr>
                ))}
                </tbody>
            </Table>

            {/* 글쓰기 버튼 */}
            <div className="d-flex justify-content-end">
                <Button variant="primary" onClick={handleWriteClick}>
                    글쓰기
                </Button>
            </div>

            {/* 페이지네이션 */}
            <div className="pagination-container">
                <ul className="pagination-list">
                    {boardList.hasPrevious && (
                        <li>
                            <Button
                                variant="link"
                                onClick={() => setCurrentPage(currentPage - 1)}
                            >
                                이전
                            </Button>
                        </li>
                    )}
                    {Array.from({length: totalPages}, (_, i) => (
                        <li key={i} className={i === currentPage ? "active" : ""}>
                            <Button
                                variant="link"
                                onClick={() => setCurrentPage(i)}
                                className="pagination-button"
                            >
                                {i + 1}
                            </Button>
                        </li>
                    ))}
                    {boardList.hasNext && (
                        <li>
                            <Button
                                variant="link"
                                onClick={() => setCurrentPage(currentPage + 1)}
                            >
                                다음
                            </Button>
                        </li>
                    )}
                </ul>
            </div>

            {/* 검색 폼 */}
            <Form
                onSubmit={(e) => {
                    e.preventDefault();
                    const keyword = e.target.keyword.value;
                    handleSearch(keyword);
                }}
                className="search-form"
            >
                <Form.Control
                    type="text"
                    name="keyword"
                    placeholder="검색어를 입력하세요"
                    className="search-input"
                />
                <Button type="submit" variant="primary" className="search-button">
                    검색
                </Button>
            </Form>
        </div>
    );
}

export default BoardList;