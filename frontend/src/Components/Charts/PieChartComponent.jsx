import {PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer} from 'recharts';
import {getMonthName} from "../../Utils/Util.js";

const PieChartComponent = ({chartData, month, year}) => {
  const COLORS = ["#0088FE", "#00C49F", "#FFBB28", "#FF8042", "#FF6384", "#36A2EB", "#FFCE56"];

  return (

    <div className="card bg-base-100 w-full shadow-lg">
      <div className="card-body">
        <h2 className="card-title">{getMonthName(month)}, {year}</h2>
        <ResponsiveContainer width="100%" height={300}>
          <PieChart>
            <Pie
              margin={{top: 20, right: 30, left: 0, bottom: 0}}
              data={chartData}
              cx="50%"
              cy="50%"
              outerRadius={100}
              dataKey="totalAmount"
              nameKey="categoryName"
            >
              {chartData.map((entry, index) => (
                <Cell key={entry.categoryName} fill={COLORS[index % COLORS.length]}></Cell>
              ))}
            </Pie>
            <Tooltip/>
            <Legend/>
          </PieChart>
        </ResponsiveContainer>
        <div className="card-actions justify-center">
          <button className="btn btn-primary">Prev Month</button>
          <button className="btn btn-primary">Next Month</button>
        </div>
      </div>
    </div>



  )
};

export default PieChartComponent;
