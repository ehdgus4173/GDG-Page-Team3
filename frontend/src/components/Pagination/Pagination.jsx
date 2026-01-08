import "./Pagination.css";

function Pagination({
  currentPage = 1,
  totalPages = 10,
  onPageChange,
}) {
  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <nav className="pagination">
      {/* 처음 / 이전 */}
      <button
        className="page-btn icon"
        disabled={currentPage === 1}
        onClick={() => onPageChange(1)}
      >
        «
      </button>

      <button
        className="page-btn icon"
        disabled={currentPage === 1}
        onClick={() => onPageChange(currentPage - 1)}
      >
        ‹
      </button>

      {/* 페이지 숫자 */}
      {pages.map((page) => (
        <button
          key={page}
          className={`page-btn ${page === currentPage ? "active" : ""}`}
          onClick={() => onPageChange(page)}
        >
          {page}
        </button>
      ))}

      {/* 다음 / 마지막 */}
      <button
        className="page-btn icon"
        disabled={currentPage === totalPages}
        onClick={() => onPageChange(currentPage + 1)}
      >
        ›
      </button>

      <button
        className="page-btn icon"
        disabled={currentPage === totalPages}
        onClick={() => onPageChange(totalPages)}
      >
        »
      </button>
    </nav>
  );
}

export default Pagination;
