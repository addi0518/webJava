import React, { useState, useEffect } from "react";
import { Container, Form, Button } from "react-bootstrap";
import { useParams } from "react-router-dom";
import axios from "axios";

function BoardWrite() {
    const { seqBoardNum } = useParams(); // 게시글 번호 파라미터 받기
    const [post, setPost] = useState({ title: "", content: "" }); // 제목과 내용을 저장하는 상태
    const [loading, setLoading] = useState(true); // 로딩 상태
    const [error, setError] = useState(null); // 에러 상태

    useEffect(() => {
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

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setPost((prevPost) => ({
            ...prevPost,
            [name]: value, // name 속성을 활용하여 상태 업데이트
        }));
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error: {error}</p>;
    }

    return (
        <Container className="write-form-container">
            <h1 className="text-center">글 수정</h1>
            <Form>
                <Form.Group className="mb-3">
                    <Form.Label>제목</Form.Label>
                    <Form.Control
                        type="text"
                        name="title"
                        value={post.title} // 상태 값을 표시
                        onChange={handleInputChange} // 변경 시 상태 업데이트
                        placeholder="제목을 입력하세요"
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>내용</Form.Label>
                    <Form.Control
                        as="textarea"
                        name="content"
                        value={post.content} // 상태 값을 표시
                        onChange={handleInputChange} // 변경 시 상태 업데이트
                        rows={6}
                        placeholder="내용을 입력하세요"
                        required
                    />
                </Form.Group>

                <div className="d-flex justify-content-between">
                    <Button variant="primary" type="submit">
                        저장
                    </Button>
                    <Button variant="secondary" type="button" onClick={() => window.history.back()}>
                        취소
                    </Button>
                </div>
            </Form>
        </Container>
    );
}

export default BoardWrite;