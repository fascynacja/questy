async function logtitles() {
     fetch("http://localhost:8080/questions/headers",{ mode: 'no-cors'})
        .then((res) => res.json())
        .then(function(headers) {
            console.log(headers);
            new gridjs.Grid({
                fixedHeader:true,
                height: "400px",
                columns: headers,
               /* data: [
                    ['John', 'john@example.com', '(353) 01 222 3333'],
                    ['Mark', 'mark@gmail.com',   '(01) 22 888 4444'],
                    ['Eoin', 'eo3n@yahoo.com',   '(05) 10 878 5554'],
                    ['Nisen', 'nis900@gmail.com',   '313 333 1923']
                ]*/
                 server: {
                     url: 'http://localhost:8080/questions/data',
                     mode:'no-cors',
                     then: data => data
                 }
            }).render(document.getElementById("wrapper"));
        });


}

logtitles();
