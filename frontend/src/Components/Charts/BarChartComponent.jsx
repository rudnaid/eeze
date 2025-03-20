import {BarChart, CartesianGrid, Bar, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer} from "recharts";
import {useNavigate} from "react-router-dom";

const BarChartComponent = ({chartData, year}) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/expenses');
  }
  return (
    <>

      <div className="card bg-base-100 w-[70%] shadow-lg mx-auto">
        <div className="card-body">
          <h2 className="card-title">Overview of {year}</h2>
          <ResponsiveContainer width="100%" height={150}>
            <BarChart
              data={chartData}
              margin={{ top: 20, right: 30, left: 0, bottom: 0 }}
            >
              <CartesianGrid strokeDasharray="3 3"/>
              <XAxis dataKey="month"/>
              <YAxis/>
              <Bar dataKey="totalIncome" fill="#5D7572" name="Total Income"/>
              <Bar dataKey="totalExpense" fill="#F57969" name="Total Expense"/>
              <Tooltip cursor={false}/>
              <Legend/>
            </BarChart>
          </ResponsiveContainer>
          <div className="card-actions justify-end">
            <button className="btn btn-primary" onClick={handleClick}>View Details</button>
          </div>
        </div>
      </div>

    </>
  );
};

export default BarChartComponent;
