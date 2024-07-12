import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./ViewPost.css";
import Header from "../Header";
import Footer from "../Footer";

function ViewPost() {
  const [posts, setPosts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [activeCategory, setActiveCategory] = useState(0);
  const [noPosts, setNoPosts] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [imageUrls, setImageUrls] = useState({});
  const navigate = useNavigate();
  const CategoryItem = ({ children, isActive, onClick }) => (
    <div className={isActive ? "active-category" : "category-item"} onClick={onClick}>
      {children}
    </div>
  )  
  const btnViewDetail = (id) => {
      navigate(`/ViewPostDetail/${id}`);
  };
  
  const Article = ({ title, excerpt, imageUrl, postId }) => (
    <article className="article">
      <img src={imageUrl} alt={title} className="article-image" />
      <h2 className="article-title">{title}</h2>
      <p className="article-excerpt">{excerpt}</p>
      <button onClick={() => btnViewDetail(postId)} className="read-more">
        Read more
      </button>
    </article>
  );
  

  useEffect(() => {
    // Fetch categories from the backend
    axios
      .get("http://localhost:8088/postCategory/getCategories")
      .then((response) => {
        setCategories([{ categoryId: 0, categoryName: "All" }, ...response.data]);
      })
      .catch((error) => {
        console.error("Error fetching categories:", error);
      });

    // Fetch all posts initially
    fetchPosts(0);
  }, []);

  const fetchPosts = (categoryID) => {
    let url = categoryID === 0
      ? "http://localhost:8088/posts/getAllActive"
      : `http://localhost:8088/posts/getActivePostByCate/${categoryID}`;

    axios
      .get(url)
      .then((response) => {
        const postsWithImages = response.data.map((post) => ({
          ...post,
          imageUrl: null, // Initialize imageUrl as null
        }));
        if (postsWithImages.length === 0) {
          setNoPosts(true);
        } else {
          setNoPosts(false);
        }
        setPosts(postsWithImages);

        // Fetch images for each post
        postsWithImages.forEach((post) => {
          fetchImagePost(post.postId);
        });
      })
      .catch((error) => {
        if (error.response && error.response.status === 404) {
          setNoPosts(true);
        } else {
          console.error(`Error fetching posts for category ${categoryID}:`, error);
        }
        setPosts([]);
      });
  };

  const fetchImagePost = async (postId) => {
    try {
      const response = await axios.get(`http://localhost:8088/postImage/${postId}/autoImg`);
      console.log(response); // Log the entire response object to the console
  
      if (response.status !== 200) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
  
      const data = response.data;
      const imageUrl = data ? data.postImageURL : null;
  
      setImageUrls((prevState) => ({
        ...prevState,
        [postId]: imageUrl,
      }));
    } catch (error) {
      console.error(`Error fetching image for post ${postId}:`, error);
    }
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

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleProfileClick = () => {
    navigate("/viewProfile");
  };

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="container">
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
                    <Article
                      title={post.title}
                      excerpt={post.content}
                      imageUrl={imageUrls[post.postId]}
                      postId={post.postId}
                    />
                  </div>
                ))}
              </div>
            ))}
          </section>
        )}
      </div>
      <Footer/>
    </>
  );
}

export default ViewPost;

