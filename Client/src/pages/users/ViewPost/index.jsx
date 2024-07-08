import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import axios from "axios";
import "./ViewPost.css";

const CategoryItem = ({ children, isActive, onClick }) => (
  <div
    className={isActive ? "active-category" : "category-item"}
    onClick={onClick}
  >
    {children}
  </div>
);

const btnViewDetail = () => {
  window.location.href = "/ViewPostDetail";
};

const Article = ({ title, excerpt, imageUrl }) => (
  <article className="article">
    <img src={imageUrl} alt={title} className="article-image" />
    <h2 className="article-title">{title}</h2>
    <p className="article-excerpt">{excerpt}</p>
    <button onClick={btnViewDetail} className="read-more">
      Read more
    </button>
  </article>
);


function ViewPost() {
  const [posts, setPosts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [activeCategory, setActiveCategory] = useState(0);
  const [noPosts, setNoPosts] = useState(false);

  useEffect(() => {
    // Fetch categories from the backend
    axios
      .get("http://localhost:8088/postCategory/getCategories")
      .then((response) => {
        setCategories([
          { categoryId: 0, categoryName: "All" },
          ...response.data,
        ]);
      })
      .catch((error) => {
        console.error("Error fetching categories:", error);
      });

    // Fetch all posts initially
    fetchPosts(0);
  }, []);

  const fetchPosts = (categoryID) => {
    let url =
      categoryID === 0
        ? "http://localhost:8088/posts/getAll"
        : `http://localhost:8088/posts/searchByCateID/${categoryID}`;

    axios
      .get(url)
      .then((response) => {
        const postsWithImages = response.data.map((post) => ({
          ...post,
          imageUrl: post.images && post.images.length > 0 ? post.images[0].url : null,
        }));
        if (postsWithImages.length === 0) {
          setNoPosts(true);
        } else {
          setNoPosts(false);
        }
        setPosts(postsWithImages);
      })
      .catch((error) => {
        if (error.response && error.response.status === 404) {
          setNoPosts(true);
        } else {
          console.error(
            `Error fetching posts for category ${categoryID}:`,
            error
          );
        }
        setPosts([]);
      });
  };

  const filterPostsByCategory = (categoryID) => {
    setActiveCategory(categoryID);
    fetchPosts(categoryID);
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
    <div className="container">
      <header className="header">
        <div className="header-content">
          <NavLink to="/" className="logo">
            MERCURY
          </NavLink>
        </div>
      </header>
      <h1 className="section-title">Recent Posts</h1>
      <div className="category-menu">
        {categories.map((category) => (
          <CategoryItem
            key={category.categoryId}
            isActive={category.categoryId === activeCategory}
            onClick={() => filterPostsByCategory(category.categoryId)}
          >
            {category.categoryName.replace(/_/g, " ")}
          </CategoryItem>
        ))}
      </div>
      {noPosts ? (
        <div className="no-posts-announcement">
          No posts available in this category.
        </div>
      ) : (
        <section className="article-grid">
          {postRows.map((row, rowIndex) => (
            <div className="article-row" key={rowIndex}>
              {row.map((post) => (
                <div className="article-column" key={post.postId}>
                  <Article title={post.title} excerpt={post.content} imageUrl={post.imageUrl} />
                </div>
              ))}
            </div>
          ))}
        </section>
      )}
    </div>
  );
}

export default ViewPost;
