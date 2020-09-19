USE qacommunity;

-- how many UNREAD notifications for one user
SELECT COUNT(*)
FROM notification
WHERE status = 0 AND notify_to_user_id = 0;
