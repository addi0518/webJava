import './App.css';
import {Navbar, Container, Nav} from 'react-bootstrap';
import {Routes, Route, Link} from 'react-router-dom';
import BoardList from "./pages/board/BoardList";
import BoardWrite from "./pages/board/BoardWrite";
import BoardDetail from './pages/board/BoardDetail';
import Home from './pages/home/Home';
import Join from "./pages/user/Join";

function App() {
  return (
    <div className="App">
      <Navbar bg="primary" data-bs-theme="dark">
        <Container>
          <Navbar.Brand href="#home">REACT-JAVA PROJECT</Navbar.Brand>
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/">Home</Nav.Link>
            <Nav.Link as={Link} to="/board/list">BoardList</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
      <Routes>
          <Route path="/board/list" element={<BoardList/>} />
          <Route path="/board/write" element={<BoardWrite />} />
          <Route path="/board/write/:seqBoardNum" element={<BoardWrite />} /> {/* 글 수정 */}
          <Route path="/board/:seqBoardNum" element={<BoardDetail />} />
          <Route path="/" element={<Home/>} />
          <Route path="/join" element={<Join/>} />
      </Routes>
    </div>
  );
}

export default App;
