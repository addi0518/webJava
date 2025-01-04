import React, { useState } from "react";
import { Form, Button, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Join(){
    const navigate = useNavigate(); // useNavigate 훅 사용
    const [formData, setFormData] = useState({
        email: "",
        name: "",
        password: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const submitForm = () => {
        axios
            .post("/join", formData)
            .then(() => {
                alert("회원가입 성공!");
                navigate("/login"); // useNavigate로 페이지 이동
            })
            .catch((error) => {
                alert("회원가입 실패!");
                console.error(error);
            });
    };

    return (
        <Container className="mt-5" style={{ maxWidth: "500px" }}>
            <h2 className="mb-4">회원가입</h2>
            <Form>
                <Form.Group className="mb-3" controlId="email">
                    <Form.Label>이메일</Form.Label>
                    <Form.Control
                        type="email"
                        name="email"
                        placeholder="이메일을 입력하세요"
                        value={formData.email}
                        onChange={ handleChange }
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="name">
                    <Form.Label>이름</Form.Label>
                    <Form.Control
                        type="text"
                        name="name"
                        placeholder="이름을 입력하세요"
                        value={formData.name}
                        onChange={ handleChange }
                        required
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="password">
                    <Form.Label>비밀번호</Form.Label>
                    <Form.Control
                        type="password"
                        name="password"
                        placeholder="비밀번호를 입력하세요"
                        value={formData.password}
                        onChange={ handleChange }
                        required
                    />
                </Form.Group>

                <Button variant="primary" onClick={ submitForm }>
                    가입하기
                </Button>
            </Form>
        </Container>
    );
}

export default Join;