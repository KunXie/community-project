/**
 * format the datetime from timestamp
 * @param timestamp
 * @returns {string}
 */
function formatDateTime(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString('en-US', { timeZone: 'America/Chicago' });
}

/**
 *
 * @param post
 * @returns {string} a post instance html
 */
const postInstanceTemplate = (post) => {
    return `<li class="list-group-item">
    <div class="media">
        <img src="${post.avatarUrl}" alt="user's avatar url" class="align-self-center rounded mr-3" width="64px" height="64px">
        <div class="media-body">
            <a class="h5" href="http://localhost:8080/post/${post.postId}" >${post.title}</a>
            <div>` +
        post.tags.map(tag => `<a href="#" class="badge badge-pill badge-info">${tag.tagName}</a>`).join(' ')
        +
        `</div>
            <div class="text-muted font-weight-light small">
                <span>${post.likeCount}</span> likes |
                <span>${post.replyCount}</span> replies |
                <span>${post.viewCount}</span> views |
                Last Modified: <span>${formatDateTime(post.gmtModified)}</span>
            </div>
        </div>
    </div>
</li>`;
}

/**
 *
 * @param pageNo
 * @param pageSize
 * @param section
 */
function fetchPostList(pageNo, pageSize, section) {
    fetch(`http://localhost:8080/posts?pageNo=${pageNo}&pageSize=${pageSize}&section=${section}`, {
        method: "get",
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            renderPostList(data, section);
            modifyPagination(data, section);
        })
        .catch(error => console.log("error", error));
}

/**
 *
 * @param pageInfo
 * @param section
 */
//TODO 这里其实没必要每次都重新渲染，直接替换值就好了, 以后有空再优化吧
function renderPostList(pageInfo, section) {
    const postListParentEle = document.querySelector(`#${section}-list`);
    postListParentEle.innerHTML = "";
    pageInfo.content.forEach(post => {
        postListParentEle.insertAdjacentHTML('beforeend', postInstanceTemplate(post));
    });
}

/**
 *
 * @param pageInfo
 * @param section
 */
// TODO 这里的hard coding, 以后再优化
function modifyPagination(pageInfo, section) {
    const currentPage = pageInfo.number;
    const firstButton = document.querySelector("#pagination #first-button");
    const prevButton = document.querySelector("#pagination #prev-button")
    const minus2Button = document.querySelector("#pagination #n2-button");
    const minus1Button = document.querySelector("#pagination #n1-button");
    const curButton = document.querySelector("#pagination #cur-button");
    const plus1Button = document.querySelector("#pagination #p1-button");
    const plus2Button = document.querySelector("#pagination #p2-button");
    const nextButton = document.querySelector("#pagination #next-button");
    const lastButton = document.querySelector("#pagination #last-button");

    // set hidden attribute
    firstButton.parentElement.hidden = pageInfo.first;
    prevButton.parentElement.hidden = pageInfo.first;
    minus2Button.parentElement.hidden = currentPage - 2 < 0;
    minus1Button.parentElement.hidden = pageInfo.first;
    plus1Button.parentElement.hidden = pageInfo.last;
    plus2Button.parentElement.hidden = currentPage + 2 > pageInfo.totalPages - 1;
    nextButton.parentElement.hidden = pageInfo.last;
    lastButton.parentElement.hidden = pageInfo.last;

    // set number
    minus2Button.textContent = currentPage - 1;
    minus1Button.textContent = currentPage;
    curButton.textContent = currentPage + 1;
    plus1Button.textContent = currentPage + 2;
    plus2Button.textContent = currentPage + 3;

    // set onclick
    firstButton.setAttribute("onClick", `fetchPostList(0, 10, '${section}')`);
    prevButton.setAttribute("onClick", `fetchPostList(${currentPage - 1}, 10, '${section}')`);
    minus2Button.setAttribute("onClick", `fetchPostList(${currentPage - 2}, 10, '${section}')`);
    minus1Button.setAttribute("onClick", `fetchPostList(${currentPage - 1}, 10, '${section}')`)

    plus1Button.setAttribute("onClick", `fetchPostList(${currentPage + 1}, 10, '${section}')`);
    plus2Button.setAttribute("onClick", `fetchPostList(${currentPage + 2}, 10, '${section}')`);
    nextButton.setAttribute("onClick", `fetchPostList(${currentPage + 1}, 10, '${section}')`);
    lastButton.setAttribute("onClick", `fetchPostList(${pageInfo.totalPages - 1}, 10, '${section}')`);
}