import React, { useState, useEffect } from "react";
import axios from "axios";
import "./ViewPost.css";
import { NavLink } from "react-router-dom";

const NavItem = ({ children }) => (
  <div className="nav-item">{children}</div>
);

const CategoryItem = ({ children, isActive, onClick }) => (
  <div
    className={isActive ? "active-category" : "category-item"}
    onClick={onClick}
  >
    {children}
  </div>
);

const ImageColumn = ({ src, alt }) => (
  <div className="image-column">
    <img loading="lazy" src={src} alt={alt} className="grid-image" />
  </div>
);

const Article = ({ title, excerpt }) => (
  <article className="article">
    <h2 className="article-title">{title}</h2>
    <p className="article-excerpt">{excerpt}</p>
    <button className="read-more">Read more</button>
  </article>
);

function ViewPost() {
  const [posts, setPosts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [activeCategory, setActiveCategory] = useState("All");

  useEffect(() => {
    // Fetch categories from the backend
    axios.get("http://localhost:8088/postCategory/getCategories")
      .then(response => {
        setCategories(response.data);
      })
      .catch(error => {
        console.error("Error fetching categories:", error);
      });

    // Fetch all posts initially
    fetchPosts("All");
  }, []);

  const fetchPosts = (categoryName) => {
    let url = categoryName === "All"
      ? "http://localhost:8088/posts/getAll"
      : `http://localhost:8088/posts/searchByCate/${categoryName}`;

    axios.get(url)
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(`Error fetching posts for category ${categoryName}:`, error);
      });
  };

  const filterPostsByCategory = (category) => {
    setActiveCategory(category);
    fetchPosts(category);
  };

  const createRows = (items, itemsPerRow) => {
    const rows = [];
    for (let i = 0; i < items.length; i += itemsPerRow) {
      rows.push(items.slice(i, i + itemsPerRow));
    }
    return rows;
  };

  const postRows = createRows(posts, 2);

  return (
    <>
      <div className="container">
        <header className="header">
          <div className="header-content">
            <NavLink to="/" className="logo">MERCURY</NavLink>
            <nav className="nav-menu">
              <NavItem>AUCTIONS</NavItem>
              <NavItem>APPRAISALS</NavItem>
              <NavItem>BUYING</NavItem>
              <NavItem>SELLING</NavItem>
              <NavItem>EXPLORE</NavItem>
              <NavItem>ABOUT</NavItem>
              <NavItem>CONTACT</NavItem>
            </nav>
          </div>
        </header>
        <h1 className="section-title">Recent Posts</h1>
        <div className="category-menu">
          {categories.map(category => (
            <CategoryItem
              key={category.categoryId}
              isActive={category.categoryName === activeCategory}
              onClick={() => filterPostsByCategory(category.categoryName)}
            >
              {category.categoryName}
            </CategoryItem>
          ))}
        </div>
        <section className="article-grid">
          {postRows.map((row, rowIndex) => (
            <div className="article-row" key={rowIndex}>
              {row.map((post, colIndex) => (
                <div className="article-column" key={post.postId}>
                  <Article
                    title={post.title}
                    excerpt={post.content}
                  />
                </div>
              ))}
            </div>
          ))}
        </section>
      </div>
    </>
  );
}

export default ViewPost;
