/*

var tabledata = [
    {id:1, name:"Oli Bob", age:"12", col:"red", dob:""},
    {id:2, name:"Mary May", age:"1", col:"blue", dob:"14/05/1982"},
    {id:3, name:"Christine Lobowski", age:"42", col:"green", dob:"22/05/1982"},
];


var table = new Tabulator("#example-table", {
     data:tabledata, //assign data to table
    columns:[ //Define Table Columns
        {title:"Name", field:"name", width:150},
        {title:"Age", field:"age", hozAlign:"left", formatter:"progress"},
        {title:"Favourite Color", field:"col"},
        {title:"Date Of Birth", field:"dob", sorter:"date", hozAlign:"center"},
    ],
    columnDefaults:{
        cellClick:function(e, cell){
                alert('test');
            cell.getColumn().getCells().forEach(function(cell){
                console.log(cell);
            })
        },
    },
});

*/


fetch("http://localhost:8080/questions/headers", {mode: 'no-cors'})
    .then((res) => res.json())
    .then(function (headers) {
        console.log(headers);

        var table = new Tabulator("#example-table", {
                ajaxURL: "http://localhost:8080/questions/data",
                columns: headers,
                tooltipsHeader: true,
                height: "300px",
                clipboard: true,
                index: "date",
                columnDefaults: {
                    width: 100,
                    headerSort: false,
                    topCalc:lastDayDifference,
                    bottomCalc:last7DaysCalc,
                    bottomCalcFormatter: bottomLastDaysFormatter

                   /* cellClick: function (e, cell) {
                        cell.getColumn().updateDefinition({cssClass: "column-selected"});
                        table.scrollToRow(cell.getRow().getIndex(), "bottom", false);
                        table.scrollToColumn( cell.getColumn().getField(), "middle", false);
                    },*/
                },

            }
        );
          table.on('cellClick', (e, cell) => {
                  const selectedCol = cell.getColumn().getField()

                  table.getColumns().forEach(function (column) {
                      if(column.getField() === selectedCol)
                      {
                          column.getCells().forEach(function (cell) {
                              cell.getElement().classList.add("column-selected");
                          });
                      }
                      else{
                          column.getCells().forEach(function (cell) {
                              cell.getElement().classList.remove("column-selected");
                          });
                      }

                  });

              })

    });

//count number of users over 18
var lastDayDifference = function(values, data, calcParams) {
    //values - array of column values
    //data - all table data
    //calcParams - params passed from the column definition object

    if (values[0] != null && values[1] != null) {
        return values[0] - values[1];
    }
    return 0;
}

var last7DaysCalc = function(values, data, calcParams) {
    //values - array of column values
    //data - all table data
    //calcParams - params passed from the column definition object
    let last7Days = 0;
    if (values[0] != null && values[5] != null) {
        last7Days =  values[0] - values[5
            ];
    }
    let last30Days = 0; // sometimes on weekend there is no data
    if (values[0] != null && values[25] != null) {
        last30Days =  values[0] - values[25];
    }

    return [last7Days, last30Days];
}


var bottomLastDaysFormatter = function(cell) {
    //values - array of column values
    //data - all table data
    //calcParams - params passed from the column definition object
    const v = cell.getValue()
    let last7Days =  v[0];
    let last30Days =v[1];

    const el = document.createElement('span');
    const last7nd = document.createTextNode(`w ` + last7Days);
    const last30nd = document.createTextNode(`m ` + last30Days);
    el.appendChild(last7nd);
    el.appendChild(document.createElement('br'));
    el.appendChild(last30nd);
    return el;
}

