(search-forward-regexp "src=\"\\(http.+\\)?\"")


<img class="decoded" src="bla/mymouse.png" id="fakeMouse" style="position: fixed; z-index:9999">

(defun download-to-local (target-path)
  "Download the specified file and change the current file to the local version"
  (interactive (list (let ((insert-default-directory nil))
                       (read-file-name "Save the file to:"))))
  (setq current-line (buffer-substring (point-at-bol) (point-at-eol)))
  (string-match "src=\"\\(http.+?\\)\"" current-line) (setq url (match-string-no-properties 1 current-line))
  (url-copy-file url target-path t)
  (goto-char (point-at-bol)) (search-forward url)(replace-match target-path nil t)
  (message "%s -> %s" url target-path))
