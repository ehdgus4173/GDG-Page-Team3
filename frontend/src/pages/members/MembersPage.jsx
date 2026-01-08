import MemberFilter from "../../components/Members/MemberFilter";
import MemberGrid from "../../components/Members/MemberGrid";
import "../../styles/layout.css";
import { useState } from "react";
import Pagination from "../../components/Pagination/Pagination";

function MembersPage() {
    const [page, setPage] = useState(1);
    const filters = [
        {
          key: "generation",
          options: ["전체", "1기", "2기", "3기", "4기", "5기"]
        },
        {
          key: "part",
          options: ["전체", "AI", "Front-end", "Back-end", "App", "Design"]
        }
      ];
      
      const [selected, setSelected] = useState({
        generation: "전체",
        part: "전체"
      });
  return (
    <section className="container">
      <h1 className="page-title">멤버</h1>

      <MemberFilter
        filters={filters}
        selected={selected}
        onChange={(key, value) =>
            setSelected((prev) => ({ ...prev, [key]: value }))
        }
        />

      <MemberGrid page={page} />

      <Pagination
        currentPage={page}
        totalPages={5}
        onPageChange={setPage}
      />
    </section>
  );
}

export default MembersPage;
