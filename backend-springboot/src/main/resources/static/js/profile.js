/**
 * TODO 这里我实现不了 根据父元素的class变化做出相应的变化
 * change the icon class of the tab links
 */
function toggleIcon(ulEle) {
    for (const liEle of ulEle.children) {
        const aEle = liEle.getElementsByTagName('a')[0];
        const iEle = aEle.getElementsByTagName('i')[0];
        if (aEle.classList.contains('active')) {
            iEle.classList.remove("far");
            iEle.classList.add("fas");
        }
        else {
            iEle.classList.remove("fas");
            iEle.classList.add("far");
        }
    }
}